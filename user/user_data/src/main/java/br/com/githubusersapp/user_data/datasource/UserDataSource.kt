package br.com.githubusersapp.user_data.datasource

import br.com.domain.model.Repo
import br.com.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataSource {

    suspend fun getUsers(): Flow<List<User>>

    suspend fun getUser(userLogin: String): Flow<User>

    suspend fun getUserRepos(userLogin: String): Flow<List<Repo>>
}