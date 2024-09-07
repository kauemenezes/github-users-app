package br.com.githubusersapp.user_presentation.sample

import br.com.domain.model.Repo
import br.com.domain.model.User
import br.com.domain.model.UserRepo

val users =
    listOf(
        User(
            id = 1,
            login = "test1",
            name = "test 1",
            avatarUrl = "url1",
        ),
        User(
            id = 2,
            login = "test2",
            name = "test 2",
            avatarUrl = "url2",
        ),
        User(
            id = 3,
            login = "test2",
            name = "test 2",
            avatarUrl = "url2",
        ),
    )

val userRepos =
    UserRepo(
        user =
            User(
                id = 1,
                login = "test1",
                name = "test 1",
                avatarUrl = "url1",
            ),
        userRepos =
            listOf(
                Repo(
                    id = 1,
                    name = "repo1",
                    fullName = "repo 1",
                    url = "url1",
                ),
                Repo(
                    id = 2,
                    name = "repo2",
                    fullName = "repo 2",
                    url = "url2",
                ),
                Repo(
                    id = 3,
                    name = "repo3",
                    fullName = "repo 3",
                    url = "url3",
                ),
            ),
    )