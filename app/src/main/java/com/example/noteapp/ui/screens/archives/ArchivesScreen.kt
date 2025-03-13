package com.example.noteapp.ui.screens.archives

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
fun ArchivesScreen(viewModel: ArchivesViewModel, onNoteClick: (noteId: Int) -> Unit) {
    val notes by viewModel.notes.collectAsState()

    ArchivesScreenContent(notes, onNoteClick)
}

@Composable
fun ArchivesScreenContent(
    notes: List<Note>,
    onNoteClick: (noteId: Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ScreenTitle("Archived Notes")
        if (notes.isNotEmpty()) {
            NoteList(notes = notes, onNoteClick = { onNoteClick(it) })
        } else {
            EmptyState()
        }
    }
}

@Composable
private fun EmptyState() {
    Text(
        "All your archived notes are stored here. You can restore or delete them anytime.",
        fontSize = 14.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Neutral950
    )
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
            "No notes have been archived yet. Move notes here for safekeeping, or create a new note.",
            fontSize = 14.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Neutral950
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WithNotes() {
    NoteAppTheme {
        val sampleNotes = listOf(
            Note(id = 1, title = "Buy groceries", text = "Milk, Eggs, Bread", true, Date()),
            Note(id = 2, title = "Workout", text = "Go for a run", true, Date()),
            Note(id = 3, title = "Study", text = "Learn Compose basics", true, Date())
        )

        ArchivesScreenContent (
            notes = sampleNotes,
            onNoteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AEmptyState() {
    NoteAppTheme {
        val sampleNotes = emptyList<Note>()

        ArchivesScreenContent(
            notes = sampleNotes,
            onNoteClick = {}
        )
    }
}