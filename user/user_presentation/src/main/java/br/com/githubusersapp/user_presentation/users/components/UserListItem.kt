package br.com.githubusersapp.user_presentation.users.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.domain.model.User
import coil.compose.rememberImagePainter
import coil.size.Scale

@Composable
fun UserListItem(
    user: User,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .wrapContentHeight()
                .fillMaxWidth(),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 4.dp,
            ),
    ) {
        Row(
            modifier =
                Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    )
                    .clickable {
                        onItemClick(user.login)
                    },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier =
                    Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(CircleShape),
                painter =
                    rememberImagePainter(
                        data = user.avatarUrl,
                        builder = {
                            crossfade(true)
                            scale(Scale.FILL)
                        },
                    ),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
            Column(
                Modifier
                    .height(IntrinsicSize.Max)
                    .padding(
                        start = 16.dp,
                    ),
            ) {
                Text(
                    text = user.login,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
