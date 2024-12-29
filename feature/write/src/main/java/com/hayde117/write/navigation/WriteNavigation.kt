package com.hayde117.write.navigation


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hayde117.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.hayde117.util.Screen
import com.hayde117.util.model.Mood
import com.hayde117.write.WriteScreen
import com.hayde117.write.WriteViewModel
//import org.koin.androidx.compose.getViewModel
import  android.widget.Toast
import org.koin.androidx.compose.getStateViewModel
import org.koin.androidx.compose.koinViewModel

//import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.writeRoute(
    onBackPressed: () -> Unit
) {

    composable(
        route = Screen.WRITE.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {

       // val viewModel: WriteViewModel = hiltViewModel()

//        val viewModel = getStateViewModel<WriteViewModel>()
        val viewModel: WriteViewModel = koinViewModel()  // Get WriteViewModel from Koin

        val uiState = viewModel.uiState
        Log.d("MY_TAG", "writeRoute uiState: ${uiState.selectedDiaryId}")


        val galleryState = viewModel.galleryState

        val pagerState = rememberPagerState()
        val pageNumber by remember { derivedStateOf { pagerState.currentPage } }
        val context = LocalContext.current

        WriteScreen(
            uiState = uiState,
            pagerState = pagerState,
            onDeleteConfirmed = {
                viewModel.deleteDiary(onSuccess = {
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                },
                    onError = { message ->
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    })
            },
            galleryState = galleryState,
            onBackPressed = onBackPressed,
            onDescriptionChanged = { viewModel.setDescription(description = it) },
            onTitleChanged = { viewModel.setTitle(title = it) },
            moodName = { Mood.values()[pageNumber].name },
            onSaveClicked = {
                viewModel.upsertDiary(
                    diary = it.apply {
                        mood = Mood.values()[pageNumber].name
                    },
                    onSuccess = { onBackPressed() },
                    onError = { message ->
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            onDateTimeUpdated = { viewModel.updateDateTime(zonedDateTime = it) },
            onImageSelect = {
                val type = context.contentResolver.getType(it)?.split("/")?.last() ?: "jpg"

                android.util.Log.d("aaa", "writeRoute: uri $it")

                viewModel.addImage(
                    image = it,
                    imageType = type
                )

            },
            onImageDeleteClicked = {
                galleryState.removeImage(it)
            }
        )
    }
}