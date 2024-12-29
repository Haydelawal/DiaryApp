package com.hayde117.diaryapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hayde117.auth.navigation.authenticationRoute
import com.hayde117.home.navigation.homeRoute
import com.hayde117.util.Screen
import com.hayde117.write.navigation.writeRoute

@Composable
fun SetUpNavGraph(
    startDestination: String,
    navController: NavHostController,
    onDataLoaded: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {

        // feature auth module
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.HOME.route)
            },
            onDataLoaded = onDataLoaded
        )

        // feature home module
        homeRoute(
            navigateToWrite = { navController.navigate(Screen.WRITE.route) },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screen.Authentication.route)
            },
            onDataLoaded = onDataLoaded,
            navigateToWriteWithArgs = {
                navController.navigate(Screen.WRITE.passDiaryId(diaryId = it))
            }
        )

        // feature write module
        writeRoute(onBackPressed = {
            navController.popBackStack()
        })
    }

}
