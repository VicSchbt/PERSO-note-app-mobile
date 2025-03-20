package com.example.noteapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.data.model.Note
import com.example.noteapp.ui.theme.Neutral100
import com.example.noteapp.ui.theme.Neutral200
import com.example.noteapp.ui.theme.Neutral700
import com.example.noteapp.utils.formatToSimpleDate

@Composable
fun NoteList(notes: List<Note>, onNoteClick: (Int) -> Unit) {
    LazyColumn {
        itemsIndexed(
            notes,
            key = { _, note -> note.id}
        ) { index, note ->
            NoteItem(onNoteClick, note)

            if (index != notes.size - 1) {
                HorizontalDivider(
                    color = Neutral200
                )
            }
        }
    }
}

@Composable
private fun NoteItem(
    onNoteClick: (Int) -> Unit,
    note: Note
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNoteClick(note.id) }
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = note.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        if (note.tags.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                items(note.tags) { tag ->
                    TagChip(tag.name)
                }
            }
        }
        Text(
            text = note.lastEdited.formatToSimpleDate(),
            color = Neutral700,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun TagChip(
    tagName: String
) {
    Box(
        modifier = Modifier.background(
                color = Neutral100,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = tagName,
            color = Neutral700,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
