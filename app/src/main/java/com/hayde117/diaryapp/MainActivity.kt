package com.hayde117.diaryapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.hayde117.diaryapp.data.repository.MongoDB
import com.hayde117.diaryapp.navigation.Screen
import com.hayde117.diaryapp.navigation.SetUpNavGraph
import com.hayde117.diaryapp.ui.theme.DiaryAppTheme
import com.hayde117.diaryapp.utils.Constants.APP_ID
import io.realm.kotlin.mongodb.App

class MainActivity : ComponentActivity() {

    private var keepSplashOpened = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {

        /** Fix White blank screen after splash screen **/
        keepSplashOpened
    }

        /** Transparent status and Nav Bar*/
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DiaryAppTheme {
                val navController = rememberNavController()
                SetUpNavGraph(
                    startDestination = getStartDestination(),
                    navController = navController,
                    onDataLoaded = {
                        keepSplashOpened = true
                    }
                )
            }
        }
    }
}

private fun getStartDestination(): String {
    val user = App.create(APP_ID).currentUser
    return if (user != null && user.loggedIn) Screen.HOME.route
    else Screen.Authentication.route
}

