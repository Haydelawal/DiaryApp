package com.hayde117.diaryapp.presentation.screens.write

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.hayde117.diaryapp.model.Diary
import com.hayde117.diaryapp.model.Mood

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WriteScreen(
    uiState: UiState,
    pagerState: PagerState,
    moodName: () -> String,
    onDeleteConfirmed: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onBackPressed: () -> Unit
){

    // Update the Mood when selecting an existing Diary
    LaunchedEffect(key1 = uiState.mood) {
        pagerState.scrollToPage(Mood.valueOf(uiState.mood.name).ordinal)
    }

    Scaffold (
        topBar = {
            WriteTopBar(selectedDiary = uiState.selectedDiary, moodName = moodName, onDeleteConfirmed = onDeleteConfirmed, onBackPressed = onBackPressed)
        },
        content = {
            WriteContent(
                pagerState = pagerState,
                title = uiState.title,
                onDescriptionChanged = onDescriptionChanged,
                onTitleChanged = onTitleChanged,
                description = uiState.description,
                paddingValues = it
            )
        }
    )
}