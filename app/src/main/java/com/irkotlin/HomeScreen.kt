package com.irkotlin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.database.*
import com.irkotlin.ui.theme.DarkGrey
import org.w3c.dom.Text
import androidx.compose.ui.Modifier
import com.irkotlin.ui.theme.BrightBlue2
import com.irkotlin.ui.theme.BrightWhite


@Composable
fun HomeScreen(eventID: String) {
    Box(modifier = Modifier
        .background(DarkGrey)
        .fillMaxSize()
    ) {
        
        Column {
            Text(text = "Your Event ID: $eventID")
            RaceInfoSection()
            CurrentPosition(eventID = String())
        }


    }

}


@Composable
fun RaceInfoSection(
) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            ){
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Race Info",
            color = BrightWhite,
            style = MaterialTheme.typography.h3

        )
    }

    }
}


@Composable
fun CurrentPosition(eventID: String) {
    val database = FirebaseDatabase.getInstance("https://iracingai-default-rtdb.europe-west1.firebasedatabase.app/")
    val currentPositionRef: DatabaseReference = database.getReference("testrace/032677/Driver Name/raceVariables/Position")

    var currentPosition by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        currentPositionRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                currentPosition = snapshot.getValue(String::class.java) ?: ""
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors here
            }
        })
    }

    Text(text = "Current Position: $currentPosition",
        style = MaterialTheme.typography.h3,
        color = BrightWhite)
}



