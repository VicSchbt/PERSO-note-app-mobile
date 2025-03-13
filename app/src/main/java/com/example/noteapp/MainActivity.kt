package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.data.database.NoteDatabase
import com.example.noteapp.data.repository.NoteRepository
import com.example.noteapp.ui.screens.all_notes_screen.AllNotesScreen
import com.example.noteapp.ui.screens.all_notes_screen.AllNotesScreenViewModel
import com.example.noteapp.ui.screens.archives.ArchivesScreen
import com.example.noteapp.ui.screens.archives.ArchivesViewModel
import com.example.noteapp.ui.screens.editor.EditorScreen
import com.example.noteapp.ui.screens.editor.EditorViewModel
import com.example.noteapp.ui.theme.Blue50
import com.example.noteapp.ui.theme.Blue500
import com.example.noteapp.ui.theme.Neutral0
import com.example.noteapp.ui.theme.Neutral100
import com.example.noteapp.ui.theme.Neutral600
import com.example.noteapp.ui.theme.NoteAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val noteDao = NoteDatabase.getDatabase(this).noteDao()
        val noteRepository = NoteRepository(noteDao)
        val editorViewModel = EditorViewModel(noteRepository)
        val allNotesViewModel = AllNotesScreenViewModel(noteRepository)
        val archivesViewModel = ArchivesViewModel(noteRepository)

        setContent {
            NoteAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Icon(
                                    painter = painterResource(R.drawable.logo),
                                    contentDescription = ""
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Neutral100
                            )
                        )
                    },
                    bottomBar = { NavigationBottomBar(
                        goToAllNotes = {navController.navigateSingleTopTo(AllNotes.route)},
                        goToSearch = {},
                        goToArchives = {navController.navigateSingleTopTo(ArchivedNotes.route)},
                        goToTag = {},
                        goToSettings = {}
                    ) },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { navController.navigateSingleTopTo(CreateNote.route) },
                            containerColor = Blue500,
                            contentColor = Neutral0
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.icon_plus),
                                contentDescription = "create a new note"
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AllNotes.route,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        composable(route = AllNotes.route) {
                            AllNotesScreen(
                                allNotesViewModel,
                                onNoteClick = { noteId: Int ->  navController.navigateSingleTopTo("${EditNote.route}/$noteId")}
                            )
                        }
                        composable(route = CreateNote.route) {
                            LaunchedEffect(null) {
                                editorViewModel.resetNote()
                            }

                            EditorScreen(
                                editorViewModel,
                                onReturnClick = { navController.popBackStack() }
                            )
                        }
                        composable(
                            route = "${EditNote.route}/{${EditNote.noteIdArg}}",
                            arguments = EditNote.arguments
                        ) {entry ->
                            val noteId = entry.arguments?.getInt(EditNote.noteIdArg) ?: return@composable
                            LaunchedEffect(noteId) {
                                editorViewModel.loadNote(noteId)
                            }

                            EditorScreen(
                                editorViewModel,
                                onReturnClick = { navController.popBackStack() }
                            )
                        }
                        composable(route = ArchivedNotes.route) {
                            ArchivesScreen(
                                archivesViewModel,
                                onNoteClick = { noteId: Int ->  navController.navigateSingleTopTo("${EditNote.route}/$noteId")}
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun NavigationBottomBar(
    goToAllNotes: () -> Unit,
    goToSearch: () -> Unit,
    goToArchives: () -> Unit,
    goToTag: () -> Unit,
    goToSettings: () -> Unit,
) {
    var activeItemId by remember { mutableStateOf("notes") }

    val navItems = listOf(
        NavigationItem("notes", R.drawable.icon_home, "Go to all notes", goToAllNotes),
        NavigationItem("search", R.drawable.icon_search, "Go to search", goToSearch),
        NavigationItem("archives", R.drawable.icon_archive, "Go to archives", goToArchives),
        NavigationItem("tag", R.drawable.icon_tag, "Go to tags", goToTag),
        NavigationItem("settings", R.drawable.icon_settings, "Go to settings", goToSettings)
    )

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        containerColor = Neutral0
    ) {
        navItems.forEach { item ->
            val isActive = item.id == activeItemId

            IconButton(
                onClick = {
                    item.onClick()
                    activeItemId = item.id
                },
                modifier = Modifier.weight(1f),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (isActive) Blue50 else Neutral0
                )
            ) {
                Icon(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = item.contentDescription,
                    tint = if (isActive) Blue500 else Neutral600
                )
            }
        }
    }
}

private data class NavigationItem(
    val id: String,
    val iconRes: Int,
    val contentDescription: String,
    val onClick: () -> Unit
)


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

@Preview(showBackground = true)
@Composable
fun NavigationBottomBarPreview() {
    NavigationBottomBar({}, {}, {}, {}, {})
}
