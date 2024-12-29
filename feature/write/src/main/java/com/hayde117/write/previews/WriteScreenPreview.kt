package com.hayde117.write.previews

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hayde117.ui.GalleryImage
import com.hayde117.ui.GalleryState
import com.hayde117.util.model.Mood
import com.hayde117.write.UiState
import com.hayde117.write.WriteScreen
import com.hayde117.write.ZoomableImage

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun WriteScreenPreview() {
    WriteScreen(
        uiState = UiState(
            mood = Mood.Happy, // Example mood
            title = "Sample Title",
            description = "Sample description for the WriteScreen preview",
            selectedDiary = null // No selected diary for preview
        ),
        pagerState = rememberPagerState(initialPage = Mood.Happy.ordinal),
        galleryState = GalleryState(),
        moodName = { "Happy" },
        onDeleteConfirmed = { /* No-op */ },
        onTitleChanged = { /* No-op */ },
        onDescriptionChanged = { /* No-op */ },
        onBackPressed = { /* No-op */ },
        onSaveClicked = { /* No-op */ },
        onDateTimeUpdated = { /* No-op */ },
        onImageSelect = { /* No-op */ },
        onImageDeleteClicked = { /* No-op */ }
    )
}

@Preview(showBackground = true)
@Composable
fun ZoomableImagePreview() {
    ZoomableImage(
        selectedGalleryImage = GalleryImage(Uri.parse("sample_image_uri")),
        onCloseClicked = { /* No-op */ },
        onDeleteClicked = { /* No-op */ }
    )
}
