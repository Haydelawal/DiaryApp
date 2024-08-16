package com.hayde117.diaryapp.presentation.screens.write

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.hayde117.diaryapp.data.repository.MongoDB
import com.hayde117.diaryapp.model.Diary
import com.hayde117.diaryapp.model.GalleryImage
import com.hayde117.diaryapp.model.GalleryState
import com.hayde117.diaryapp.model.Mood
import com.hayde117.diaryapp.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.hayde117.diaryapp.model.RequestState
import com.hayde117.diaryapp.utils.toRealmInstant
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class WriteViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val galleryState = GalleryState()

    var uiState by mutableStateOf(UiState())
        private set

    init {
        getDiaryIdArgument()
        fetchSelectedDiary()
    }

    private fun getDiaryIdArgument() {
        uiState = uiState.copy(
            selectedDiaryId = savedStateHandle.get<String>(
                key = WRITE_SCREEN_ARGUMENT_KEY
            )
        )
    }

    private fun fetchSelectedDiary() {
        if (uiState.selectedDiaryId != null) {
            viewModelScope.launch(Dispatchers.Main) {
                MongoDB.getSelectedDiary(diaryId = ObjectId.from(uiState.selectedDiaryId!!))
                    .catch { emit(RequestState.Error(Exception("Diary is already deleted."))) }
                    .collect { diary ->
                        if (diary is RequestState.Success) {
                            setSelectedDiary(diary = diary.data)
                            setMood(mood = Mood.valueOf(diary.data.mood))
                            setTitle(title = diary.data.title)
                            setDescription(description = diary.data.description)

                        }
                    }
            }
        }
    }


    fun setTitle(title: String) {
        uiState = uiState.copy(title = title)
    }

    fun setDescription(description: String) {
        uiState = uiState.copy(description = description)
    }

    private fun setMood(mood: Mood) {
        uiState = uiState.copy(mood = mood)
    }

    private fun setSelectedDiary(diary: Diary) {
        uiState = uiState.copy(selectedDiary = diary)
    }

    /** Trigger Update Or Insert Function **/
    fun upsertDiary(
        diary: Diary,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            if (uiState.selectedDiaryId != null) {
                updateDiary(diary = diary, onSuccess = onSuccess, onError = onError)
            } else {
                insertDiary(diary = diary, onSuccess = onSuccess, onError = onError)
            }
        }
    }


    private suspend fun insertDiary(
        diary: Diary,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val result = MongoDB.insertDiary(diary = diary.apply {
            if (uiState.updatedDateTime != null) {
                date = uiState.updatedDateTime!!
            }
        })

        if (result is RequestState.Success) {
            uploadImagesToFirebase()

            withContext(Dispatchers.Main) {
                onSuccess()
            }
        } else if (result is RequestState.Error) {
            withContext(Dispatchers.Main) {
                onError(result.error.message.toString())
            }
        }

    }

    private suspend fun updateDiary(
        diary: Diary,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val result = MongoDB.updateDiary(diary = diary.apply {
            _id = ObjectId.from(uiState.selectedDiaryId!!)

            date = if (uiState.updatedDateTime != null) {
                uiState.updatedDateTime!!
            } else {
                uiState.selectedDiary!!.date
            }

        })

        if (result is RequestState.Success) {
            uploadImagesToFirebase()

            withContext(Dispatchers.Main) {
                onSuccess()
            }
        } else if (result is RequestState.Error) {
            withContext(Dispatchers.Main) {
                onError(result.error.message.toString())
            }
        }
    }

    fun updateDateTime(zonedDateTime: ZonedDateTime) {
        uiState = uiState.copy(updatedDateTime = zonedDateTime.toInstant().toRealmInstant())
    }


    fun deleteDiary(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (uiState.selectedDiaryId != null) {

                val result =
                    MongoDB.deleteDiary(id = ObjectId.from(uiState.selectedDiaryId!!))

                if (result is RequestState.Success) {
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                } else if (result is RequestState.Error) {
                    withContext(Dispatchers.Main) {
                        onError(result.error.message.toString())
                    }
                }
            }
        }
    }

    fun addImage(image: Uri, imageType: String) {
        val remoteImagePath = "images/${FirebaseAuth.getInstance().currentUser?.uid}/" +
                "${image.lastPathSegment}-${System.currentTimeMillis()}.$imageType"

        Log.d("aaaa", "addImage: $remoteImagePath")

        galleryState.addImage(
            GalleryImage(
                image = image,
                remoteImagePath = remoteImagePath
            )
        )
    }


    private fun uploadImagesToFirebase() {
        val storage = FirebaseStorage.getInstance().reference

        galleryState.images.forEach { galleryImage ->
            val imagePath = storage.child(galleryImage.remoteImagePath)
            imagePath.putFile(galleryImage.image)

//                .addOnProgressListener {
//                    val sessionUri = it.uploadSessionUri
//                    if (sessionUri != null) {
//                        viewModelScope.launch(Dispatchers.IO) {
//                            imageToUploadDao.addImageToUpload(
//                                ImageToUpload(
//                                    remoteImagePath = galleryImage.remoteImagePath,
//                                    imageUri = galleryImage.image.toString(),
//                                    sessionUri = sessionUri.toString()
//                                )
//                            )
//                        }
//                    }
//                }


        }


    }

}

data class UiState(
    val selectedDiaryId: String? = null,
    val selectedDiary: Diary? = null,
    val title: String = "",
    val description: String = "",
    val mood: Mood = Mood.Neutral,
    val updatedDateTime: RealmInstant? = null
)