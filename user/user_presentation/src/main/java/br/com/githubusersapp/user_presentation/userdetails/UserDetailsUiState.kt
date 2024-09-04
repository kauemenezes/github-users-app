package br.com.githubusersapp.user_presentation.userdetails

import androidx.annotation.StringRes
import br.com.domain.model.UserRepo

data class UserDetailsUiState(
    val user: UserRepo? = null,
    @StringRes val errorMessage: Int? = null,
    val isLoading: Boolean = false,
)
