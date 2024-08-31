package br.com.githubusersapp.user_presentation.users

import androidx.annotation.StringRes
import br.com.domain.model.User

data class UsersUiState(
    val users: List<User> = emptyList(),
    val hasNoUsers: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val isLoading: Boolean = false
)
