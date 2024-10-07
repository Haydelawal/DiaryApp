package com.hayde117.diaryapp.presentation.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.hayde117.util.connectivity.ConnectivityObserver
import com.hayde117.util.connectivity.NetworkConnectivityObserver
import com.hayde117.diaryapp.data.database.ImageToDeleteDao
import com.hayde117.diaryapp.data.database.entity.ImageToDelete
import com.hayde117.diaryapp.data.repository.Diaries
import com.hayde117.diaryapp.data.repository.MongoDB
import com.hayde117.util.model.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.N)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val connectivity: NetworkConnectivityObserver,
    private val imageToDeleteDao: ImageToDeleteDao
) : ViewModel() {

    /** using this to manually cancel coroutine job **/
    private lateinit var allDiariesJob: Job

    /** using this to manually cancel coroutine job **/
    private lateinit var filteredDiariesJob: Job

    var diaries: MutableState<Diaries> = mutableStateOf(RequestState.Idle)
    private var network by mutableStateOf(ConnectivityObserver.Status.Unavailable)

    var dateIsSelected by mutableStateOf(false)
        private set


    init {
        getDiaries()
        viewModelScope.launch {
            connectivity.observe().collect { network = it }
        }
    }


    fun getDiaries(zonedDateTime: ZonedDateTime? = null) {
        dateIsSelected = zonedDateTime != null
        diaries.value = RequestState.Loading
        if (dateIsSelected && zonedDateTime != null) {
            observeFilteredDiaries(zonedDateTime = zonedDateTime)
        } else {
            observeAllDiaries()
        }
    }

    private fun observeFilteredDiaries(zonedDateTime: ZonedDateTime) {
       filteredDiariesJob = viewModelScope.launch {

           /** using this to manually cancel coroutine job **/
           if (::allDiariesJob.isInitialized) {
               allDiariesJob.cancelAndJoin()
           }

            MongoDB.getFilteredDiaries(zonedDateTime = zonedDateTime).collect { result ->
                diaries.value = result
            }
        }
    }


    private fun observeAllDiaries() {
      allDiariesJob =  viewModelScope.launch(Dispatchers.Main) {

          /** using this to manually cancel coroutine job **/
          if (::filteredDiariesJob.isInitialized) {
              filteredDiariesJob.cancelAndJoin()
          }

            MongoDB.getAllDiaries().collect { result ->
                diaries.value = result
            }
        }
    }


    fun deleteAllDiaries(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        if (network == ConnectivityObserver.Status.Available) {

            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val imagesDirectory = "images/${userId}"

            val storage = FirebaseStorage.getInstance().reference
            storage.child(imagesDirectory)
                .listAll()

                .addOnSuccessListener {
                    it.items.forEach { ref ->
                        val imagePath = "images/${userId}/${ref.name}"
                        storage.child(imagePath).delete()
                            .addOnFailureListener {
                                viewModelScope.launch(Dispatchers.IO) {
                                    imageToDeleteDao.addImageToDelete(
                                        ImageToDelete(
                                            remoteImagePath = imagePath
                                        )
                                    )
                                }
                            }
                    }

                    viewModelScope.launch(Dispatchers.IO) {
                        val result = MongoDB.deleteAllDiaries()

                        if (result is RequestState.Success) {
                            withContext(Dispatchers.Main) {
                                onSuccess()
                            }
                        } else if (result is RequestState.Error) {
                            withContext(Dispatchers.Main) {
                                onError(result.error)
                            }
                        }
                    }
                }
                .addOnFailureListener { onError(it) }
        } else {
            onError(Exception("No Internet Connection."))
        }
    }

}

