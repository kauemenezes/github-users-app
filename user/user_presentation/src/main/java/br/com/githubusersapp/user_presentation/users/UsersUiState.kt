package br.com.githubusersapp.user_presentation.users

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import br.com.githubusersapp.user_domain.model.User

@Stable
data class UsersUiState(
    val users: List<User> = emptyList(),
    val hasNoUsers: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val isLoading: Boolean = false,
)
