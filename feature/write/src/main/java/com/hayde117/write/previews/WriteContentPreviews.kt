package com.hayde117.write.previews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hayde117.ui.GalleryState
import com.hayde117.write.UiState
import com.hayde117.write.WriteContent

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun WriteContentPreview() {
    val fakeUiState = UiState(
        title = "Sample Title",
        description = "Sample description for the diary entry."
    )

    val fakePagerState = rememberPagerState(initialPage = 0)
    val fakeGalleryState = GalleryState()

    WriteContent(
        uiState = fakeUiState,
        pagerState = fakePagerState,
        title = fakeUiState.title,
        galleryState = fakeGalleryState,
        onDescriptionChanged = {},
        onTitleChanged = {},
        description = fakeUiState.description,
        paddingValues = PaddingValues(),
        onSaveClicked = { /* Save action mock */ },
        onImageSelect = { /* Image selection mock */ },
        onImageClicked = { /* Image clicked mock */ }
    )
}
