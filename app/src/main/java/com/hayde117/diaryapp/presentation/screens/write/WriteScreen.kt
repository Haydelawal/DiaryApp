package com.hayde117.diaryapp.presentation.screens.write

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.hayde117.diaryapp.model.Diary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WriteScreen(
    pagerState: PagerState,
    selectedDiary: Diary?,
    onDeleteConfirmed: () -> Unit,
    onBackPressed: () -> Unit
){

    Scaffold (
        topBar = {
            WriteTopBar(selectedDiary = selectedDiary, onDeleteConfirmed = onDeleteConfirmed, onBackPressed = onBackPressed)
        },
        content = {
            WriteContent(
                pagerState = pagerState,
                title = "",
                onDescriptionChanged = {},
                onTitleChanged = {},
                description = "",
                paddingValues = it
            )
        }
    )
}