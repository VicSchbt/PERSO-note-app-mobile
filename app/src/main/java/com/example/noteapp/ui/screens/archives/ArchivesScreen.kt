package com.example.noteapp.ui.screens.archives

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.data.model.Note
import com.example.noteapp.ui.composables.NoteList
import com.example.noteapp.ui.composables.ScreenTitle

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
        NoteList(notes = notes, onNoteClick = { onNoteClick(it) })
    }
}