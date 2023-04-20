package com.irkotlin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.firebase.database.*
import java.util.*

@Composable
fun EventIDScreen(database: FirebaseDatabase) {
    val database = FirebaseDatabase.getInstance("https://iracingai-default-rtdb.europe-west1.firebasedatabase.app/")
    val racesRef = database.getReference("testrace")
    var events by remember { mutableStateOf(emptyList<String>()) }
    var expanded by remember { mutableStateOf(false) }
    var selectedEvent by remember { mutableStateOf("") }

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

    Column {
        // Show selected event or prompt to create new event
        if (selectedEvent.isNotEmpty()) {
            Text("Selected Event: $selectedEvent")
        } else {
            Text("Please select an event or create a new one")
        }

        // Dropdown selection menu for events
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = { expanded = true }) {
                Text("Select Event")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                events.forEach { event ->
                    DropdownMenuItem(onClick = {
                        selectedEvent = event
                        expanded = false
                    }) {
                        Text(event)
                    }
                }
            }
        }

        // Button to create new event
        Button(onClick = {
            val newEventID = "event-${UUID.randomUUID()}"
            racesRef.child(newEventID).setValue("")

            selectedEvent = newEventID
            expanded = false
        }) {
            Text("Create New Event")
        }
    }
}
