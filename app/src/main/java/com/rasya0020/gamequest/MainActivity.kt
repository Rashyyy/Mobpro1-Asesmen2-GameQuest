package com.rasya0020.gamequest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rasya0020.gamequest.navigation.NavGraph
import com.rasya0020.gamequest.ui.theme.GameQuestTheme
import com.rasya0020.gamequest.ui.theme.screen.MainViewModel
import com.rasya0020.gamequest.util.ViewModelFactory
import com.rasya0020.gamequest.database.GameDb

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val dao = GameDb.getInstance(context).gameDao()
            val viewModel: MainViewModel = viewModel(
                factory = ViewModelFactory(dao, context)
            )
            val isDarkMode by viewModel.isDarkMode.collectAsState()
            GameQuestTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    NavGraph()
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
    GameQuestTheme {
        Greeting("Android")
    }
}