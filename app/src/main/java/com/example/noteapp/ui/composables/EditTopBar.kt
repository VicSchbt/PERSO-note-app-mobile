package com.example.noteapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.R
import com.example.noteapp.ui.theme.Blue500
import com.example.noteapp.ui.theme.Neutral600

@Composable
fun EditTopBar(
    isEditMode: Boolean,
    onReturnClick: () -> Unit,
    onCancelClick: () -> Unit,
    isSaveEnabled: Boolean,
    onSaveClick: () -> Unit,
    isArchived: Boolean,
    onArchiveClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        GoBackButton(onReturnClick)

        Row {
            if (isEditMode) {
                EditActions(
                    onDeleteClick = onDeleteClick,
                    onArchiveClick = onArchiveClick,
                    isArchived = isArchived
                )
            }
            TextButton(
                onClick = { onCancelClick() },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Neutral600
                )
            ) {
                Text(
                    "Cancel",
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            TextButton(
                onClick = { onSaveClick() },
                enabled = isSaveEnabled,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Blue500
                )
            ) {
                Text(
                    "Save Note",
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

    }
}

@Composable
private fun EditActions(
    onDeleteClick: () -> Unit,
    onArchiveClick: () -> Unit,
    isArchived: Boolean
) {
    IconButton(
        onClick = { onDeleteClick() },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_delete),
            contentDescription = "Delete current note",
            tint = Neutral600,
            modifier = Modifier.size(18.dp)
        )
    }
    IconButton(
        onClick = { onArchiveClick() },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_archive),
            contentDescription = "Archive current note",
            tint = if (isArchived) Blue500 else Neutral600,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun GoBackButton(onReturnClick: () -> Unit) {
    TextButton(
        onClick = {
            onReturnClick()
        },
        colors = ButtonDefaults.textButtonColors(
            contentColor = Neutral600
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_arrow_left),
            contentDescription = ""
        )
        Text(
            text = "Go Back",
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.Normal
        )
    }
}