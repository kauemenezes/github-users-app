package br.com.githubusersapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.githubusersapp.user_presentation.userdetails.USER_DETAILS_ARGUMENT_KEY
import br.com.githubusersapp.user_presentation.userdetails.UserDetailsScreen
import br.com.githubusersapp.user_presentation.users.UsersScreen

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.User.route
    ) {
        composable(route = Screen.User.route) {
            UsersScreen(
                onNavigateToUserDetails = { userLogin ->
                    navController.navigate(route = Screen.UserDetails.passUserLogin(userLogin))
                }
            )
        }
        composable(
            route = Screen.UserDetails.route,
            arguments = listOf(navArgument(USER_DETAILS_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) {
            UserDetailsScreen(
                onBackButtonClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}