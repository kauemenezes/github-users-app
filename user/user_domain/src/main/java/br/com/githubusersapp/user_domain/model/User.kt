package br.com.githubusersapp.user_domain.model

data class User(
    val id: Long,
    val name: String,
    val login: String,
    val avatarUrl: String,
)
