package com.hayde117.diaryapp.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hayde117.diaryapp.presentation.screens.auth.AuthenticationScreen
import com.hayde117.diaryapp.presentation.screens.auth.AuthenticationViewmodel
import com.hayde117.diaryapp.utils.Constants.APP_ID
import com.hayde117.diaryapp.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SetUpNavGraph(startDestination: String, navController: NavHostController){

    NavHost(
        navController= navController,
        startDestination= startDestination,
        ){
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.HOME.route)
            }
        )
        homeRoute()
        writeRoute()
    }

}

fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit
){
    composable(route = Screen.Authentication.route){

        val viewmodel: AuthenticationViewmodel = viewModel()
        val authenticated by viewmodel.authenticated

        val loadingState by viewmodel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        AuthenticationScreen(
            authenticated= authenticated,
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
                        }
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
fun NavGraphBuilder.homeRoute(){
    composable(route = Screen.HOME.route){

        // dummy log out code, will be removed later
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(onClick = {
                scope.launch(Dispatchers.IO) {
                    App.create(APP_ID).currentUser?.logOut()
                }
            }) {
                Text(text = "Log Out")
            }
        }


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