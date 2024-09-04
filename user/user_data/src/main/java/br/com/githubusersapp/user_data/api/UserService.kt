package br.com.githubusersapp.user_data.api

import br.com.githubusersapp.user_data.model.RepoResponse
import br.com.githubusersapp.user_data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users")
    suspend fun getUsers(): List<UserResponse>

    @GET("users/{login}")
    suspend fun getUser(
        @Path("login") userLogin: String,
    ): UserResponse

    @GET("users/{login}/repos")
    suspend fun getUserRepos(
        @Path("login") userLogin: String,
    ): List<RepoResponse>

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}
