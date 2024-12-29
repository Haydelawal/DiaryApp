package com.hayde117.util.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hayde117.util.model.Diary
import com.hayde117.util.model.DiaryHolder
import com.hayde117.util.model.Mood

@Preview(showBackground = true)
@Composable
fun DiaryHolderPreview(){
    DiaryHolder(diary = Diary().apply {
        title = "My Diaryyy"
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
        mood = Mood.Happy.name
    }, onClick = {})
}