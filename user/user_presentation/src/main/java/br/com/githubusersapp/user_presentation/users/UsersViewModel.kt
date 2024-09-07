package br.com.githubusersapp.user_presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.domain.model.User
import br.com.githubusersapp.user_domain.usecase.GetUsersUseCase
import br.com.githubusersapp.user_presentation.util.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class UsersViewModel
    @Inject
    constructor(
        private val getUsersUseCase: GetUsersUseCase,
        private val dispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(UsersUiState())
        val uiState = _uiState.asStateFlow()

        private val _searchText = MutableStateFlow("")
        val searchText = _searchText.asStateFlow()

        init {
            getUsers()
        }

        private fun getUsers() {
            viewModelScope.launch(dispatcher) {
                _uiState.update {
                    it.copy(isLoading = true)
                }
                getUsersUseCase()
                    .catch { error ->
                        _uiState.update {
                            it.copy(
                                errorMessage = ExceptionParser.getMessage(error),
                                isLoading = false,
                            )
                        }
                    }
                    .collect { users ->
                        setupUsers(users)
                    }
            }
        }

        private suspend fun setupUsers(users: List<User>) {
            searchText
                .debounce(500L)
                .collectLatest { query ->
                    val filteredUsers =
                        if (query.isNotBlank()) {
                            users.filter {
                                it.name.contains(query, true) || it.login.contains(query, true)
                            }
                        } else {
                            users
                        }
                    _uiState.update {
                        it.copy(
                            users = filteredUsers,
                            hasNoUsers = filteredUsers.isEmpty(),
                            errorMessage = null,
                            isLoading = false,
                        )
                    }
                }
        }

        fun onSearchTextChange(text: String) {
            _searchText.value = text
        }
    }
