package com.hayde117.home.previews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hayde117.home.DateHeader
import com.hayde117.home.EmptyPage
import com.hayde117.home.HomeContent
import com.hayde117.util.model.Diary
import com.hayde117.util.model.Mood
import java.time.LocalDate


@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    val fakeDiaries = mapOf(
        LocalDate.now() to listOf(
            Diary()
        )
    )

    HomeContent(
        paddingValues = PaddingValues(0.dp), // Assuming no padding needed
        diaryNotes = fakeDiaries,
        onClick = {} // Empty lambda for preview
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyPagePreview() {
    EmptyPage()
}

@Preview(showBackground = true)
@Composable
fun DateHeaderPreview(){
    DateHeader(localDate = LocalDate.now())
}