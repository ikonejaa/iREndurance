package com.irkotlin

sealed class Screen(val route: String) {
    object EventIDScreen : Screen("eventID_screen")
    object HomeScreen : Screen("home_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}

