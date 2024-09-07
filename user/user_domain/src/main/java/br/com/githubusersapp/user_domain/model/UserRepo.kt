package br.com.githubusersapp.user_domain.model

data class UserRepo(
    val user: User,
    val userRepos: List<Repo>,
)
