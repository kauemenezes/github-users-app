package br.com.githubusersapp.user_presentation.users

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.githubusersapp.user_presentation.R
import br.com.githubusersapp.user_presentation.components.EmptyState
import br.com.githubusersapp.user_presentation.components.ErrorState
import br.com.githubusersapp.user_presentation.components.ProgressIndicator
import br.com.githubusersapp.user_presentation.users.components.UserListItem

@Composable
fun UsersScreen(
    onNavigateToUserDetails: (String) -> Unit,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UsersScreen(searchText, uiState, viewModel::onSearchTextChange) { userLogin ->
        onNavigateToUserDetails(userLogin)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UsersScreen(
    searchText: String,
    uiState: UsersUiState,
    onSearchQueryChange: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.users_screen_title)
                    )
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                TextField(
                    value = searchText,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    placeholder = { Text(text = "Search") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.background
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                when {
                    uiState.isLoading -> {
                        ProgressIndicator()
                    }

                    uiState.errorMessage != null -> {
                        ErrorState(errorMessage = stringResource(id = uiState.errorMessage))
                    }

                    uiState.hasNoUsers -> {
                        EmptyState()
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                items = uiState.users,
                                key = { user ->
                                    user.id
                                }
                            ) { user ->
                                UserListItem(user = user, onItemClick = onItemClick)
                            }
                        }
                    }
                }
            }
        }
    )
}