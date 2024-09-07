package br.com.githubusersapp.sample

import br.com.githubusersapp.user_domain.model.Repo
import br.com.githubusersapp.user_domain.model.User
import br.com.githubusersapp.user_domain.model.UserRepo

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
            login = "test3",
            name = "test 3",
            avatarUrl = "url3",
        ),
    )

val userRepos =
    UserRepo(
        user =
            User(
                id = 1,
                login = "test3",
                name = "test 3",
                avatarUrl = "url3",
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