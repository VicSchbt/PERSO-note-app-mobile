package com.example.noteapp

import androidx.navigation.NavType
import androidx.navigation.navArgument

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

object EditNote: NoteDestinations {
    override val route = "edit"
    const val noteIdArg = "note_id"
    val arguments = listOf(
        navArgument(noteIdArg) { type = NavType.IntType }
    )
}

object ArchivedNotes: NoteDestinations {
    override val route = "archives"
}