package br.com.githubusersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.githubusersapp.ui.navigation.MainNavigation
import br.com.githubusersapp.ui.theme.GithubUsersAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubUsersAppTheme {
                Surface(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .semantics {
                                testTagsAsResourceId = true
                            },
                    color = MaterialTheme.colorScheme.background,
                ) {
                    navController = rememberNavController()
                    MainNavigation(navController = navController)
                }
            }
        }
    }
}