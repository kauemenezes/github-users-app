package br.com.githubusersapp.user_domain.repository

import br.com.domain.model.User
import br.com.domain.model.UserRepo
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsers(): Flow<List<User>>

    suspend fun getUserDetails(userLogin: String): Flow<UserRepo>
}