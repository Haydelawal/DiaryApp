package com.hayde117.util.previews

import android.net.Uri
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hayde117.ui.GalleryImage
import com.hayde117.ui.GalleryState
import com.hayde117.util.model.AddImageButton
import com.hayde117.util.model.Gallery
import com.hayde117.util.model.GalleryUploader
import com.hayde117.util.model.LastImageOverlay

@Preview(showBackground = true)
@Composable
fun GalleryPreview_SmallImages() {
    val mockUris = List(3) { Uri.parse("android.resource://your.package.name/drawable/sample_image") }
    Gallery(
        images = mockUris,
        imageSize = 40.dp,
        spaceBetween = 10.dp
    )
}

@Preview(showBackground = true)
@Composable
fun GalleryPreview_LargeImages() {
    val mockUris = List(5) { Uri.parse("android.resource://your.package.name/drawable/sample_image") }
    Gallery(
        images = mockUris,
        imageSize = 80.dp,
        spaceBetween = 16.dp
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AddImageButtonPreview() {
    AddImageButton(
        imageSize = 60.dp,
        imageShape = RoundedCornerShape(8.dp),
        onClick = { /* Handle add image click */ }
    )
}

@Preview(showBackground = true)
@Composable
fun GalleryUploaderPreview_EmptyState() {
    GalleryUploader(
        galleryState = GalleryState(),
        onAddClicked = { /* Handle add image click */ },
        onImageSelect = { /* Handle image select */ },
        onImageClicked = { /* Handle image click */ }
    )
}

@Preview(showBackground = true)
@Composable
fun GalleryUploaderPreview_WithImages() {
    val mockGalleryImages = List(5) {
        GalleryImage(
            remoteImagePath = it.toString(),
            image = Uri.parse("android.resource://your.package.name/drawable/sample_image")
        )
    }
    GalleryUploader(
        galleryState = GalleryState(),
        onAddClicked = { /* Handle add image click */ },
        onImageSelect = { /* Handle image select */ },
        onImageClicked = { /* Handle image click */ }
    )
}

@Preview(showBackground = true)
@Composable
fun LastImageOverlayPreview() {
    LastImageOverlay(
        imageSize = 60.dp,
        imageShape = RoundedCornerShape(8.dp),
        remainingImages = 5
    )
}


