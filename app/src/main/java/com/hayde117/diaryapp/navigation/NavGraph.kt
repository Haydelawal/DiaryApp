package com.hayde117.diaryapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hayde117.diaryapp.presentation.screens.auth.AuthenticationScreen
import com.hayde117.diaryapp.presentation.screens.auth.AuthenticationViewmodel
import com.hayde117.diaryapp.presentation.screens.home.HomeScreen
import com.hayde117.diaryapp.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun SetUpNavGraph(startDestination: String, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.HOME.route)
            }
        )
        homeRoute(navigateToWrite = { navController.navigate(Screen.WRITE.route) })
        writeRoute()
    }

}

fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit
) {
    composable(route = Screen.Authentication.route) {

        val viewmodel: AuthenticationViewmodel = viewModel()
        val authenticated by viewmodel.authenticated

        val loadingState by viewmodel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

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

    ) {
    composable(route = Screen.HOME.route) {

        HomeScreen(onMenuClicked = {}, navigateToWrite = navigateToWrite)

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