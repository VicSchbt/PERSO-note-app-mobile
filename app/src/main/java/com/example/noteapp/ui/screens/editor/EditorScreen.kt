package com.example.noteapp.ui.screens.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.noteapp.config.ModalConfig
import com.example.noteapp.ui.composables.ConfirmationModal
import com.example.noteapp.ui.composables.EditForm
import com.example.noteapp.ui.composables.EditTopBar
import java.util.Date

@Composable
fun EditorScreen(
    viewModel: EditorViewModel,
    onReturnClick: () -> Unit
) {
    val noteState = viewModel.note.collectAsStateWithLifecycle()

    var title by rememberSaveable(noteState.value?.id) { mutableStateOf(noteState.value?.title.orEmpty()) }
    var text by rememberSaveable(noteState.value?.id) { mutableStateOf(noteState.value?.text.orEmpty()) }
    val isArchived = noteState.value?.isArchived ?: false
    val lastEdited = noteState.value?.lastEdited

    CreateNoteContent(
        isEditMode = noteState.value != null,
        title = title,
        onTitleChange = { title = it },
        text = text,
        onTextChange = { text = it },
        onReturnClick = { onReturnClick() },
        onSaveClick = { viewModel.saveNote(title, text, isArchived) },
        isArchived,
        toggleIsArchived = {
            noteState.value?.let { viewModel.toggleNoteArchive(it.id) }
        },
        onDeleteClick = {
            viewModel.deleteCurrentNote()
            onReturnClick()
        },
        lastEdited
    )
}

@Composable
fun CreateNoteContent(
    isEditMode: Boolean,
    title: String,
    onTitleChange: (String) -> Unit,
    text: String,
    onTextChange: (String) -> Unit,
    onReturnClick: () -> Unit,
    onSaveClick: () -> Unit,
    isArchived: Boolean,
    toggleIsArchived: () -> Unit,
    onDeleteClick: () -> Unit,
    lastEdited: Date?
) {
    var openDeleteDialog by remember { mutableStateOf(false) }
    var openArchiveDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        EditTopBar(
            isEditMode = isEditMode,
            onReturnClick = { onReturnClick() },
            onCancelClick = { onReturnClick() },
            isSaveEnabled = (title != ""),
            onSaveClick = { onSaveClick() },
            isArchived = isArchived,
            onArchiveClick = {
                if (isArchived) {
                    toggleIsArchived()
                } else {
                    openArchiveDialog = true
                }
            },
            onDeleteClick = { openDeleteDialog = true }
        )

        HorizontalDivider(
            modifier = Modifier.padding(
                horizontal = 16.dp
            )
        )

        EditForm(title, onTitleChange, text, onTextChange, lastEdited)
    }
    if (openDeleteDialog) {
        ConfirmationModal(
            config = ModalConfig.DELETE,
            onDismissRequest = { openDeleteDialog = false },
            onCancelClick = { openDeleteDialog = false },
            onValidateClick = {
                openDeleteDialog = false
                onDeleteClick()

            }
        )
    }
    if (openArchiveDialog) {
        ConfirmationModal(
            config = ModalConfig.ARCHIVE,
            onDismissRequest = { openArchiveDialog = false },
            onCancelClick = { openArchiveDialog = false },
            onValidateClick = {
                openArchiveDialog = false
                toggleIsArchived()
            }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun CreateNoteScreenPreview() {
    CreateNoteContent(
        true,
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
        {},
        false,
        {},
        {},
        Date()
    )
}
