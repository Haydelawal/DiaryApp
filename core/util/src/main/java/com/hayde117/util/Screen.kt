package com.hayde117.util

import com.hayde117.util.Constants.WRITE_SCREEN_ARGUMENT_KEY


sealed class Screen(val route: String) {

    object Authentication: Screen(route = "authentication_screen")
    object HOME: Screen(route = "home_screen")
    object WRITE: Screen(route = "write_screen?$WRITE_SCREEN_ARGUMENT_KEY={$WRITE_SCREEN_ARGUMENT_KEY}"){

        fun passDiaryId(diaryId: String) : String = "write_screen?$WRITE_SCREEN_ARGUMENT_KEY=$diaryId"

    }
}