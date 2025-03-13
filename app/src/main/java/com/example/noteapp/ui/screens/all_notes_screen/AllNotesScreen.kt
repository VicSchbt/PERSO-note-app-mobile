package com.example.noteapp.ui.screens.all_notes_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.data.model.Note
import com.example.noteapp.ui.composables.NoteList
import com.example.noteapp.ui.composables.ScreenTitle
import com.example.noteapp.ui.theme.NoteAppTheme

@Composable
fun AllNotesScreen(viewModel: AllNotesScreenViewModel, onNoteClick: (noteId: Int) -> Unit) {
    val notes by viewModel.notes.collectAsState()

    AllNotesScreenContent(notes, onNoteClick)
}

@Composable
fun AllNotesScreenContent(
    notes: List<Note>,
    onNoteClick: (noteId: Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ScreenTitle("All notes")
        NoteList(notes = notes, onNoteClick = { onNoteClick(it) })
    }
}

@Preview(showBackground = true)
@Composable
fun AllNotesScreenPreview() {
    NoteAppTheme {
        val sampleNotes = listOf(
            Note(id = 1, title = "Buy groceries", text = "Milk, Eggs, Bread"),
            Note(id = 2, title = "Workout", text = "Go for a run"),
            Note(id = 3, title = "Study", text = "Learn Compose basics")
        )

        AllNotesScreenContent(
            notes = sampleNotes,
            onNoteClick = {}
        )
    }
}