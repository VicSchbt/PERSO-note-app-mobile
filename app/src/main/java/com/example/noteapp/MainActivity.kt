package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.data.database.NoteDatabase
import com.example.noteapp.data.repository.NoteRepository
import com.example.noteapp.ui.screens.all_notes_screen.AllNotesScreen
import com.example.noteapp.ui.screens.all_notes_screen.AllNotesScreenViewModel
import com.example.noteapp.ui.screens.create_note_screen.CreateNoteScreen
import com.example.noteapp.ui.screens.create_note_screen.CreateNoteScreenViewModel
import com.example.noteapp.ui.theme.NoteAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val noteDao = NoteDatabase.getDatabase(this).noteDao()
        val noteRepository = NoteRepository(noteDao)
        val createNoteViewModel = CreateNoteScreenViewModel(noteRepository)
        val allNotesViewModel = AllNotesScreenViewModel(noteRepository)

        setContent {
            NoteAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomAppBar(
                            actions = {
                                Button(onClick = { navController.navigateSingleTopTo("all") }) {
                                    Text("All Note")
                                }
                                Button(onClick = { navController.navigateSingleTopTo("create") }) {
                                    Text("Create Note")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AllNotes.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = AllNotes.route) {
                            AllNotesScreen(
                                allNotesViewModel
                            )
                        }
                        composable(route = CreateNote.route) {
                            CreateNoteScreen(
                                createNoteViewModel,
                                onReturnClick = { navController.popBackStack() }
                            )
                        }

                    }

                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        NoteAppTheme {
            Greeting("Android")
        }
    }

}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }
