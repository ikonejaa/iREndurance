package com.irkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase
import com.irkotlin.ui.theme.IrKotlinTheme
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


class MainActivity : ComponentActivity() {

    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance("https://iracingai-default-rtdb.europe-west1.firebasedatabase.app/")

        setContent {
            IrKotlinTheme {
                MaterialTheme(
                    colors = darkColors(
                        primary = colorResource(id = R.color.BrightRed),
                        secondary = colorResource(id = R.color.OceanBlue),
                        background = colorResource(id = R.color.DarkGrey),
                    ),
                    typography = Typography(
                        h1 = TextStyle(fontSize = 96.sp, fontFamily = FontFamily.Default),
                        h2 = TextStyle(fontSize = 60.sp, fontFamily = FontFamily.Default),
                        h3 = TextStyle(fontSize = 48.sp, fontFamily = FontFamily.Default),
                        h4 = TextStyle(fontSize = 34.sp, fontFamily = FontFamily.Default),
                        h5 = TextStyle(fontSize = 24.sp, fontFamily = FontFamily.Default),
                        h6 = TextStyle(fontSize = 20.sp, fontFamily = FontFamily.Default),
                        subtitle1 = TextStyle(fontSize = 16.sp, fontFamily = FontFamily.Default),
                        subtitle2 = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Default),
                        body1 = TextStyle(fontSize = 16.sp, fontFamily = FontFamily.Default),
                        body2 = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Default),
                        button = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Default),
                        caption = TextStyle(fontSize = 12.sp, fontFamily = FontFamily.Default),
                        overline = TextStyle(fontSize = 10.sp, fontFamily = FontFamily.Default),
                    ),
                    shapes = Shapes(
                        small = RoundedCornerShape(4.dp),
                        medium = RoundedCornerShape(8.dp),
                        large = RoundedCornerShape(16.dp),
                    ),
                ) {
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = Screen.EventIDScreen.route ) {
                            composable(route = Screen.EventIDScreen.route) {
EventIDScreen(database = database, navController = navController, eventID = String())
                            }
                            composable(
                                route = Screen.HomeScreen.route + "/{eventID}",
                                arguments = listOf(
                                    navArgument("eventID") {
                                        type = NavType.StringType
                                        defaultValue = "EventID should be here"
                                        nullable = true
                                    }
                                )
                            ){entry ->
                                val eventID = entry.arguments?.getString("eventID") ?: "EventID should be here"

                                HomeScreen(eventID = eventID)

                            }
                        }

                }


        }
    }
}}
