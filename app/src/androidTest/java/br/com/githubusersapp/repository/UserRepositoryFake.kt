package br.com.githubusersapp.repository

import br.com.githubusersapp.sample.userRepos
import br.com.githubusersapp.sample.users
import br.com.githubusersapp.user_domain.model.User
import br.com.githubusersapp.user_domain.model.UserRepo
import br.com.githubusersapp.user_domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserRepositoryFake : UserRepository {
    override suspend fun getUsers(): Flow<List<User>> {
        return flowOf(users)
    }

    override suspend fun getUserDetails(userLogin: String): Flow<UserRepo> {
        return flowOf(userRepos)
    }
}