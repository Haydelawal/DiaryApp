package com.hayde117.auth.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hayde117.auth.AuthenticationContent

@Preview(showBackground = true)
@Composable
fun AuthenticationContentPreview_Loading() {
    AuthenticationContent(
        loadingState = true,
        onButtonClicked = { /* Handle button click */ }
    )
}

@Preview(showBackground = true)
@Composable
fun AuthenticationContentPreview_NotLoading() {
    AuthenticationContent(
        loadingState = false,
        onButtonClicked = { /* Handle button click */ }
    )
}
