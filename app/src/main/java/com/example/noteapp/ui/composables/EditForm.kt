package com.example.noteapp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.ui.theme.Neutral800
import com.example.noteapp.ui.theme.Neutral950

@Composable
fun EditForm(
    title: String,
    onTitleChange: (String) -> Unit,
    text: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        value = title,
        onValueChange = { onTitleChange(it) },
        placeholder = { Text("Enter a title...") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedPlaceholderColor = Neutral950,
            focusedPlaceholderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        ),
        textStyle = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )
    )
    HorizontalDivider(
        modifier = Modifier.padding(
            horizontal = 16.dp
        )
    )
    OutlinedTextField(
        value = text,
        onValueChange = { onTextChange(it) },
        placeholder = { Text("Start typing your note here...") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedPlaceholderColor = Neutral800,
            focusedPlaceholderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        ),
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 18.sp
        )
    )
}