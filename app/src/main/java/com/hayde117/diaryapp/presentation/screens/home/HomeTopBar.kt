package com.hayde117.diaryapp.presentation.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hayde117.diaryapp.R
import com.hayde117.diaryapp.ui.theme.DiaryAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onMenuClicked: () -> Unit
) {
    
    TopAppBar(
        scrollBehavior = scrollBehavior, title = { Text(text = stringResource(R.string.diary)) },
        navigationIcon = {
            IconButton(onClick = { onMenuClicked() }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = stringResource(R.string.hamburger_menu), tint = MaterialTheme.colorScheme.onSurface )
            }
        },
        actions = {
            IconButton(onClick = { onMenuClicked() }) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = stringResource(R.string.date_icon), tint = MaterialTheme.colorScheme.onSurface )
            }
        })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
//        HomeTopBar(onMenuClicked = {})
    }