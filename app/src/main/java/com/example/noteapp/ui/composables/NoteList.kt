package com.example.noteapp.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.data.model.Note
import com.example.noteapp.ui.theme.Neutral200

@Composable
fun NoteList(notes: List<Note>, onNoteClick: (Int) -> Unit) {
    LazyColumn {
        itemsIndexed(notes) { index, note ->
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
            }

            if (index != notes.size - 1) {
                HorizontalDivider(
                    color = Neutral200
                )
            }
        }
    }
}