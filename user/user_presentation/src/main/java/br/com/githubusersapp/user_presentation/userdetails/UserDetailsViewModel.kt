package br.com.githubusersapp.user_presentation.userdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.githubusersapp.user_domain.model.screen.UserDetails
import br.com.githubusersapp.user_domain.usecase.GetUserDetailsUseCase
import br.com.githubusersapp.user_presentation.util.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val getUserDetailsUseCase: GetUserDetailsUseCase,
        private val dispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(UserDetailsUiState())
        val uiState = _uiState.asStateFlow()

        init {
            val userLogin: String = savedStateHandle.toRoute<UserDetails>().userLogin
            getUserDetails(userLogin)
        }

        private fun getUserDetails(userLogin: String) {
            viewModelScope.launch(dispatcher) {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                    )
                }
                getUserDetailsUseCase(userLogin)
                    .catch { error ->
                        _uiState.update {
                            it.copy(
                                errorMessage = ExceptionParser.getMessage(error),
                                isLoading = false,
                            )
                        }
                    }
                    .collect { userDetails ->
                        _uiState.update {
                            it.copy(
                                user = userDetails,
                                errorMessage = null,
                                isLoading = false,
                            )
                        }
                    }
            }
        }
    }
