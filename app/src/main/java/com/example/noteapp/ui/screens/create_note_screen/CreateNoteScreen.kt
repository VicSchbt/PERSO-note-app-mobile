package com.example.noteapp.ui.screens.create_note_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.R
import com.example.noteapp.ui.theme.Blue500
import com.example.noteapp.ui.theme.Neutral600
import com.example.noteapp.ui.theme.Neutral800
import com.example.noteapp.ui.theme.Neutral950

@Composable
fun CreateNoteScreen(
    viewModel: CreateNoteScreenViewModel,
    onReturnClick: () -> Unit
) {

    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }

    CreateNoteContent(
        title = title,
        onTitleChange = { title = it },
        text = text,
        onTextChange = { text = it },
        onReturnClick = { onReturnClick() },
        onSaveClick = { viewModel.saveNote(title, text) }
    )
}

@Composable
fun CreateNoteContent(
    title: String,
    onTitleChange: (String) -> Unit,
    text: String,
    onTextChange: (String) -> Unit,
    onReturnClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        EditTopBar(
            onReturnClick = { onReturnClick() },
            onCancelClick = { onReturnClick() },
            isSaveEnabled = (title != ""),
            onSaveClick = { onSaveClick() }
        )

        HorizontalDivider(
            modifier = Modifier.padding(
                horizontal = 16.dp
            )
        )

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
}

@Composable
fun EditTopBar(
    onReturnClick: () -> Unit,
    onCancelClick: () -> Unit,
    isSaveEnabled: Boolean,
    onSaveClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
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

        Row {
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

@Preview(showBackground = true)
@Composable
fun CreateNoteScreenPreview() {
    CreateNoteContent(
        "React Performance Optimization",
        {},
        "Key performance optimization techniques:\n" +
                "\n" +
                "1. Code Splitting\n" +
                "- Use React.lazy() for route-based splitting\n" +
                "- Implement dynamic imports for heavy components\n" +
                "\n" +
                "2.\tMemoization\n" +
                "- useMemo for expensive calculations\n" +
                "- useCallback for function props\n" +
                "- React.memo for component optimization\n" +
                "\n" +
                "3. Virtual List Implementation\n" +
                "- Use react-window for long lists\n" +
                "- Implement infinite scrolling\n" +
                "\n" +
                "TODO: Benchmark current application and identify bottlenecks",
        {},
        {},
        {})
}
