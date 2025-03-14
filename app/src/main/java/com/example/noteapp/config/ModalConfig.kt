package com.example.noteapp.config

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.noteapp.R
import com.example.noteapp.ui.theme.Blue500
import com.example.noteapp.ui.theme.Red500

enum class ModalConfig(
    @DrawableRes val leadingIconRes: Int,
    @StringRes val title: Int,
    @StringRes val text: Int,
    @ColorInt val accentColor: Color,
    @StringRes val validateCTA: Int
) {
    DELETE(
        R.drawable.icon_delete,
        R.string.modal_delete_title,
        R.string.modal_delete_text,
        Red500,
        R.string.modal_delete_cta
    ),
    ARCHIVE(
        R.drawable.icon_archive,
        R.string.modal_archive_title,
        R.string.modal_archive_text,
        Blue500,
        R.string.modal_archive_cta
    )
}
