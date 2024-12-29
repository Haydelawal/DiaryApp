package com.hayde117.home.previews

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hayde117.home.HomeTopBar
import java.time.ZonedDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeTopBarPreview_NoDateSelected() {
    HomeTopBar(
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        onMenuClicked = { /* Handle menu click */ },
        dateIsSelected = false,
        onDateSelected = { /* Handle date selection */ },
        onDateReset = { /* Handle date reset */ }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeTopBarPreview_DateSelected() {
    HomeTopBar(
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        onMenuClicked = { /* Handle menu click */ },
        dateIsSelected = true,
        onDateSelected = { /* Handle date selection */ },
        onDateReset = { /* Handle date reset */ }
    )
}

