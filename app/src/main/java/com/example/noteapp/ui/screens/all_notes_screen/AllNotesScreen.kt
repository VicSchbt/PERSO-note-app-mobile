package com.example.noteapp.ui.screens.all_notes_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AllNotesScreen(viewModel: AllNotesScreenViewModel, onNoteClick: (noteId: Int) -> Unit) {
    val notes by viewModel.notes.collectAsState()
    Column {
        Text("All notes", fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 29.sp)
        LazyColumn {
            itemsIndexed(notes) { index, note ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = { onNoteClick(note.id) }
                        )
                        .padding(vertical = 8.dp)
                ) {
                    Text(note.title,
                        fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                if (index != notes.size -1) {
                    HorizontalDivider()
                }
            }
        }
    }
}