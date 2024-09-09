package br.com.githubusersapp.user_domain.model.screen

import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val userLogin: String,
)
