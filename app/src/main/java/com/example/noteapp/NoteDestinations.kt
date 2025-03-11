package com.example.noteapp

import androidx.compose.runtime.Composable
import com.example.noteapp.ui.screens.all_notes_screen.AllNotesScreen
import com.example.noteapp.ui.screens.create_note_screen.CreateNoteScreen

/**
 * Contract for information needed on every Note navigation destination
 */
interface NoteDestinations {
    val route: String
}

/**
 * Note App navigation destination
 */
object AllNotes: NoteDestinations {
    override val route = "all"
}

object CreateNote: NoteDestinations {
    override val route = "create"
}