package com.hayde117.diaryapp.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hayde117.diaryapp.data.repository.Diaries
import com.hayde117.diaryapp.data.repository.MongoDB
import com.hayde117.diaryapp.utils.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    var diaries: MutableState<Diaries> = mutableStateOf(RequestState.Idle)

    init {
        observeAllDiaries()
    }

    private fun observeAllDiaries() {
        viewModelScope.launch(Dispatchers.IO) {
            MongoDB.getAllDiaries().collect { result ->
                diaries.value = result
            }
        }
    }



}

