package com.example.noteapp.ui.screens.all_notes_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.data.model.Note
import com.example.noteapp.ui.composables.NoteList
import com.example.noteapp.ui.composables.ScreenTitle
import com.example.noteapp.ui.theme.Neutral100
import com.example.noteapp.ui.theme.Neutral200
import com.example.noteapp.ui.theme.Neutral950
import com.example.noteapp.ui.theme.NoteAppTheme
import java.util.Date

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
        if (notes.isNotEmpty()) {
            NoteList(notes = notes, onNoteClick = { onNoteClick(it) })
        } else {
            EmptyState()
        }

    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier
            .background(Neutral100, shape = RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Neutral200,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            "You don’t have any notes yet. Start a new note to capture your thoughts and ideas.",
            fontSize = 14.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Neutral950
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AllNotesScreenPreview() {
    NoteAppTheme {
        val sampleNotes = listOf(
            Note(id = 1, title = "Buy groceries", text = "Milk, Eggs, Bread", false, Date()),
            Note(id = 2, title = "Workout", text = "Go for a run", false, Date()),
            Note(id = 3, title = "Study", text = "Learn Compose basics", false, Date())
        )

        AllNotesScreenContent(
            notes = sampleNotes,
            onNoteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AllNotesScreenPreviewEmptyState() {
    NoteAppTheme {
        val sampleNotes = emptyList<Note>()

        AllNotesScreenContent(
            notes = sampleNotes,
            onNoteClick = {}
        )
    }
}