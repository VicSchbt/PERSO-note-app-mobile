package com.example.noteapp.ui.screens.all_notes_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun AllNotesScreen(viewModel: AllNotesScreenViewModel) {
    val notes by viewModel.notes.observeAsState(emptyList())
    Column {
        Text("All notes")
        LazyColumn {
            items(notes) { note ->
                Text(note.title)
            }
        }
    }
}