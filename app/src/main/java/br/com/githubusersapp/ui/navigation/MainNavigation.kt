package br.com.githubusersapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.githubusersapp.user_domain.model.screen.UserDetails
import br.com.githubusersapp.user_domain.model.screen.Users
import br.com.githubusersapp.user_presentation.userdetails.UserDetailsScreen
import br.com.githubusersapp.user_presentation.users.UsersScreen

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Users,
    ) {
        composable<Users> {
            UsersScreen(
                onNavigateToUserDetails = { userLogin ->
                    navController.navigate(route = UserDetails(userLogin))
                },
            )
        }
        composable<UserDetails> {
            UserDetailsScreen(
                onBackButtonClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}