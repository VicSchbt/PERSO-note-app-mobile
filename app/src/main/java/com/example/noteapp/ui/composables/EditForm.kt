package com.example.noteapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.R
import com.example.noteapp.ui.theme.Neutral400
import com.example.noteapp.ui.theme.Neutral700
import com.example.noteapp.ui.theme.Neutral800
import com.example.noteapp.ui.theme.Neutral950
import com.example.noteapp.utils.formatToSimpleDate
import java.util.Date

@Composable
fun EditForm(
    title: String,
    onTitleChange: (String) -> Unit,
    text: String,
    onTextChange: (String) -> Unit,
    lastEdited: Date?
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
    LastEditedInfo(lastEdited)
    HorizontalDivider(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp
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

@Composable
private fun LastEditedInfo(lastEdited: Date?) {
    val dateText = lastEdited?.formatToSimpleDate() ?: "Not yet saved"
    val dateColor = if (lastEdited != null) Neutral700 else Neutral400

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_clock),
                contentDescription = "",
                tint = Neutral700,
                modifier = Modifier.size(16.dp)
            )
            Text(
                "Last edited",
                color = Neutral700,
                fontSize = 12.sp,
                lineHeight = 14.sp
            )
        }
        Text(
            text = dateText,
            color = dateColor,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            modifier = Modifier.weight(2f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditFormPreviewNoDate() {
    Column(modifier = Modifier.fillMaxWidth()) {
        EditForm(
            title = "Le titre de la note",
            {},
            text = "Ceci est le corps de la note.",
            {},
            null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditFormPreviewWithDate() {
    Column(modifier = Modifier.fillMaxWidth()) {
        EditForm(
            title = "Le titre de la note",
            {},
            text = "Ceci est le corps de la note.",
            {},
            Date()
        )
    }
}