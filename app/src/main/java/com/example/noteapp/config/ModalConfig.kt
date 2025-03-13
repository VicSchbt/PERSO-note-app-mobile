package com.example.noteapp.config

import androidx.compose.ui.graphics.Color
import com.example.noteapp.R
import com.example.noteapp.ui.theme.Blue500
import com.example.noteapp.ui.theme.Red500

enum class ModalConfig(
    val leadingIconRes: Int,
    val title: String,
    val text: String,
    val accentColor: Color,
    val validateCTA: String
) {
    DELETE(
        R.drawable.icon_delete,
        "Delete Note",
        "Are you sure you want to permanently delete this note? This action cannot be undone.",
        Red500,
        "Delete"
    ),
    ARCHIVE(
        R.drawable.icon_archive,
        "Archive Note",
        "Are you sure you want to archive this note? You can find it in the Archived Notes section and restore it anytime.",
        Blue500,
        "Archive"
    )
}
