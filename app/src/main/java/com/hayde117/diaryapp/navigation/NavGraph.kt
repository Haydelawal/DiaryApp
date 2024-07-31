package com.hayde117.diaryapp.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hayde117.diaryapp.presentation.screens.auth.AuthenticationScreen
import com.hayde117.diaryapp.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY

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

        /** hard coding value of false temporarily*/
        AuthenticationScreen(loadingState = false, onButtonClicked = {})
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