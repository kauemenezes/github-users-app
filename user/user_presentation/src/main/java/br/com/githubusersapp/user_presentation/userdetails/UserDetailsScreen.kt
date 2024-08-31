package br.com.githubusersapp.user_presentation.userdetails

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.domain.model.UserRepo
import br.com.githubusersapp.user_presentation.R
import br.com.githubusersapp.user_presentation.components.EmptyState
import br.com.githubusersapp.user_presentation.components.ProgressIndicator
import br.com.githubusersapp.user_presentation.userdetails.components.RepositoryListItem
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.Scale

const val USER_DETAILS_ARGUMENT_KEY = "userLogin"

@Composable
fun UserDetailsScreen(
    onBackButtonClick: () -> Unit,
    viewModel: UserDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserDetailsScreen(onBackButtonClick, uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserDetailsScreen(
    onBackButtonClick: () -> Unit,
    uiState: UserDetailsUiState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBackButtonClick() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Icon"
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.users_details_screen_title)
                    )
                }
            )
        },
        content = { scaffoldPadding ->
            when {
                uiState.isLoading -> {
                    ProgressIndicator()
                }

                uiState.errorMessage != null -> {
                    EmptyState(message = stringResource(id = uiState.errorMessage))
                }

                else -> {
                    uiState.user?.let { user ->
                        UserDetailsContent(user, modifier = Modifier.padding(scaffoldPadding))
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun UserDetailsContent(userRepo: UserRepo, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Image(
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape),
            painter = rememberImagePainter(
                data = userRepo.user.avatarUrl, builder = {
                    crossfade(true)
                    scale(Scale.FILL)
                }),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = userRepo.user.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = userRepo.user.login,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = userRepo.userRepos,
                    key = { repos ->
                        repos.id
                    }
                ) { repo ->
                    RepositoryListItem(
                        repo = repo,
                        onItemClick = {
                            openRepository(context, repo.url)
                        }
                    )
                }
            }
        }
    }
}

fun openRepository(context: Context, repoUrl: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(repoUrl)
    ContextCompat.startActivity(context, intent, null)
}