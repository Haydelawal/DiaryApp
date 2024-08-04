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
import com.hayde117.diaryapp.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun SetUpNavGraph(startDestination: String, navController: NavHostController){

    NavHost(
        navController= navController,
        startDestination= startDestination,
        ){
        authenticationRoute()
        homeRoute()
        writeRoute()
    }

}

fun NavGraphBuilder.authenticationRoute(){
    composable(route = Screen.Authentication.route){

        val viewmodel: AuthenticationViewmodel = viewModel()
        val loadingState by viewmodel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        AuthenticationScreen(
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
                        if (it){
                            messageBarState.addSuccess("Successfully Authenticated!")
                            viewmodel.setLoading(false)
                        }

                    },
                    onError = {
                        messageBarState.addError(it)
                        viewmodel.setLoading(false)
                    }
                )
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
            }
        )
    }
}
fun NavGraphBuilder.homeRoute(){
    composable(route = Screen.HOME.route){

    }
}
fun NavGraphBuilder.writeRoute(){
    composable(route = Screen.WRITE.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ){

    }
}