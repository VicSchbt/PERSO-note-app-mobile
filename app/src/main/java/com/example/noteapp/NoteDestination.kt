package com.example.noteapp

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NoteDestination(val route: String) {

    data object AllNotes : NoteDestination("notes")

    data object CreateNote : NoteDestination("create")

    data object EditNote : NoteDestination("edit") {
        const val noteIdArg = "note_id"
        val arguments = listOf(
            navArgument(noteIdArg) { type = NavType.IntType }
        )

    }

    data object SearchNote : NoteDestination("search")

    data object TagNote : NoteDestination("tag")

    data object Settings : NoteDestination("settings")

    data object ArchivedNotes : NoteDestination("archives")
}
