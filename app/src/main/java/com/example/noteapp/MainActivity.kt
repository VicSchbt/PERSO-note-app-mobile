package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.ui.res.stringResource
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
                NoteScaffold(navController, allNotesViewModel, editorViewModel, archivesViewModel)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteScaffold(
    navController: NavHostController,
    allNotesViewModel: AllNotesScreenViewModel,
    editorViewModel: EditorViewModel,
    archivesViewModel: ArchivesViewModel
) {
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
        bottomBar = {
            NavigationBottomBar(
                goToAllNotes = { navController.navigateSingleTopTo(NoteDestination.AllNotes.route) },
                goToSearch = {},
                goToArchives = { navController.navigateSingleTopTo(NoteDestination.ArchivedNotes.route) },
                goToTag = {},
                goToSettings = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigateSingleTopTo(NoteDestination.CreateNote.route) },
                containerColor = Blue500,
                contentColor = Neutral0
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_plus),
                    contentDescription = stringResource(R.string.nav_new_note_content_descr)
                )
            }
        }
    ) { innerPadding ->
        NoteNavGraph(
            navController,
            allNotesViewModel,
            editorViewModel,
            archivesViewModel,
            modifier = Modifier.padding(innerPadding)
        )

    }
}

@Composable
private fun NoteNavGraph(
    navController: NavHostController,
    allNotesViewModel: AllNotesScreenViewModel,
    editorViewModel: EditorViewModel,
    archivesViewModel: ArchivesViewModel,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NoteDestination.AllNotes.route,
        modifier = modifier,
    ) {
        composable(route = NoteDestination.AllNotes.route) {
            AllNotesScreen(
                allNotesViewModel,
                onNoteClick = { noteId: Int -> navController.navigateSingleTopTo("${NoteDestination.EditNote.route}/$noteId") }
            )
        }
        composable(route = NoteDestination.CreateNote.route) {
            LaunchedEffect(null) {
                editorViewModel.resetNote()
            }

            EditorScreen(
                editorViewModel,
                onReturnClick = { navController.popBackStack() }
            )
        }
        composable(
            route = "${NoteDestination.EditNote.route}/{${NoteDestination.EditNote.noteIdArg}}",
            arguments = NoteDestination.EditNote.arguments
        ) { entry ->
            val noteId =
                entry.arguments?.getInt(NoteDestination.EditNote.noteIdArg) ?: return@composable
            LaunchedEffect(noteId) {
                editorViewModel.loadNote(noteId)
            }

            EditorScreen(
                editorViewModel,
                onReturnClick = { navController.popBackStack() }
            )
        }
        composable(route = NoteDestination.ArchivedNotes.route) {
            ArchivesScreen(
                archivesViewModel,
                onNoteClick = { noteId: Int -> navController.navigateSingleTopTo("${NoteDestination.EditNote.route}/$noteId") }
            )
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
        NavigationItem(
            NoteDestination.AllNotes.route,
            R.drawable.icon_home,
            stringResource(R.string.nav_all_notes_content_descr),
            goToAllNotes
        ),
        NavigationItem(
            NoteDestination.SearchNote.route,
            R.drawable.icon_search,
            stringResource(R.string.nav_search_content_descr),
            goToSearch
        ),
        NavigationItem(
            NoteDestination.ArchivedNotes.route,
            R.drawable.icon_archive,
            stringResource(R.string.nav_archives_content_descr),
            goToArchives
        ),
        NavigationItem(
            NoteDestination.TagNote.route, R.drawable.icon_tag,
            stringResource(R.string.nav_tags_content_descr), goToTag
        ),
        NavigationItem(
            NoteDestination.Settings.route,
            R.drawable.icon_settings,
            stringResource(R.string.nav_settings_content_descr),
            goToSettings
        )
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
