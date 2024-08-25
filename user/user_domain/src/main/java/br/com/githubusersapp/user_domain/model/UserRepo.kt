package br.com.domain.model

data class UserRepo(
    val user: User,
    val userRepos: List<Repo>
)
