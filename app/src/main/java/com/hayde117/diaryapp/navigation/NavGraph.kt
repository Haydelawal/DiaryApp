package com.hayde117.diaryapp.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hayde117.diaryapp.data.repository.MongoDB
import com.hayde117.diaryapp.presentation.components.DisplayAlertDialog
import com.hayde117.diaryapp.presentation.screens.auth.AuthenticationScreen
import com.hayde117.diaryapp.presentation.screens.auth.AuthenticationViewmodel
import com.hayde117.diaryapp.presentation.screens.home.HomeScreen
import com.hayde117.diaryapp.presentation.screens.home.HomeViewModel
import com.hayde117.diaryapp.utils.Constants.APP_ID
import com.hayde117.diaryapp.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.hayde117.diaryapp.utils.RequestState
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SetUpNavGraph(
    startDestination: String, navController: NavHostController, onDataLoaded: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.HOME.route)
            },
            onDataLoaded = onDataLoaded
        )
        homeRoute(
            navigateToWrite = { navController.navigate(Screen.WRITE.route) },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screen.Authentication.route)
            },
            onDataLoaded = onDataLoaded
        )
        writeRoute()
    }

}

fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit,
    onDataLoaded: () -> Unit
    ) {
    composable(route = Screen.Authentication.route) {

        val viewmodel: AuthenticationViewmodel = viewModel()
        val authenticated by viewmodel.authenticated

        val loadingState by viewmodel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        LaunchedEffect(key1 = Unit) {
            onDataLoaded()
        }

        AuthenticationScreen(
            authenticated = authenticated,
            loadingState = loadingState,
            oneTapState = oneTapState,
            onButtonClicked = {
                oneTapState.open()
                viewmodel.setLoading(true)
            },
            messageBarState = messageBarState,
            onTokenIdReceived = { tokenId ->
                viewmodel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Authenticated!")
                        viewmodel.setLoading(false)
                    },
                    onError = {
                        messageBarState.addError(it)
                        viewmodel.setLoading(false)
                    }
                )
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                viewmodel.setLoading(false)
            },
            navigateToHome = navigateToHome

        )
    }
}

fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
    navigateToAuth: () -> Unit,
    onDataLoaded: () -> Unit,
) {
    composable(route = Screen.HOME.route) {
        val viewModel: HomeViewModel = viewModel()
        val diaries by viewModel.diaries

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var signOutDialogOpened by remember {
            mutableStateOf(false)
        }
        val scope = rememberCoroutineScope()

        LaunchedEffect(key1 = diaries) {
            if (diaries !is RequestState.Loading) {
                onDataLoaded()
            }
        }

        HomeScreen(
            diaries = diaries,
            onMenuClicked = {
                scope.launch(Dispatchers.IO) {
                    drawerState.open()
                }
            }, navigateToWrite = navigateToWrite, drawerState = drawerState, onSignOutClicked = {
                signOutDialogOpened = true
            })

        DisplayAlertDialog(
            title = "Sign Out",
            message = "Are You Sure You Want To Sign Out From Your Google Account?",
            dialogOpened = signOutDialogOpened,
            onCloseDialog = { signOutDialogOpened = false },
            onYesClicked = {
                scope.launch(Dispatchers.IO) {
                    val user = App.create(APP_ID).currentUser

                    if (user != null) {
                        user.logOut()
                        withContext(Dispatchers.Main) {
                            navigateToAuth()
                        }

                    }
                }
            }
        )

    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        route = Screen.WRITE.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {

    }
}