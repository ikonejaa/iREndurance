package com.irkotlin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.firebase.database.*
import java.util.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem


import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.irkotlin.ui.theme.DarkGrey

@Composable
fun EventIDScreen(
    database: FirebaseDatabase,
    navController: NavController,
    eventID: String

) {
    val database = FirebaseDatabase.getInstance("https://iracingai-default-rtdb.europe-west1.firebasedatabase.app/")
    val racesRef = database.getReference("testrace")
    var events by remember { mutableStateOf(emptyList<String>()) }
    var expanded by remember { mutableStateOf(false) }
    var eventID by remember { mutableStateOf("") }

    // Retrieve the list of events from the database when the screen is first displayed
    LaunchedEffect(Unit) {
        racesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val eventIDs = snapshot.children.mapNotNull { it.key }
                events = eventIDs
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White),
    verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Show selected event or prompt to create new event
        if (eventID.isNotEmpty()) {
            Text(
                text = "Selected Event: $eventID",
                color = Color.DarkGray,
                style = MaterialTheme.typography.h5
            )
        } else {
            Text(
                text = "Please select an event or create a new one",
                color = Color.DarkGray,
                style = MaterialTheme.typography.h5
            )
        }

        // Dropdown selection menu for events
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { expanded = true },
                content = {
                    Text(
                        text = "Select Event",
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                events.forEach { event ->
                    DropdownMenuItem(
                        onClick = {
                            eventID = event
                            expanded = false
                        },
                        content = {
                            Text(
                                text = event,
                                color = Color.DarkGray,
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                    )
                }
            }
        }

        // Button to create new event
        Button(
            onClick = {
                val random = Random()
                val newEventID = (1..6).map { random.nextInt(10) }.joinToString("")
                val driverName = "Driver Name"
                val eventRef = racesRef.child(newEventID)
                val driverRef = eventRef.child(driverName)
                driverRef.child("raceVariables").setValue("")

                eventID = newEventID
                expanded = false
            },
            modifier = Modifier.background(Color.Yellow)
        ) {
            Text(
                text = "Create New Event",
                color = Color.White,
                style = MaterialTheme.typography.button
            )
        }

        // Button to navigate to HomeScreen
        Button(
            onClick = {
                navController.navigate(Screen.HomeScreen.route + "/${eventID}") },
            modifier = Modifier.background(Color.Blue)
        ) {
            Text(
                text = "Go to Home Screen",
                color = Color.White,
                style = MaterialTheme.typography.button
            )

        }
    }
}
