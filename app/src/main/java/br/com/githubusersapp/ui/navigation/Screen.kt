package br.com.githubusersapp.ui.navigation

sealed class Screen(val route: String) {
    object User : Screen("user_screen")
    object UserDetails : Screen("user_details_screen/{userLogin}") {
        fun passUserLogin(userLogin: String) = "user_details_screen/$userLogin"
    }
}
