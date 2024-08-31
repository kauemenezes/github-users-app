package br.com.githubusersapp.user_data.datasource

import br.com.domain.model.Repo
import br.com.domain.model.User
import br.com.githubusersapp.user_data.api.UserService
import br.com.githubusersapp.user_data.mapper.RepoResponseMapper
import br.com.githubusersapp.user_data.mapper.UserResponseMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserDataSourceImpl(
    private val userService: UserService,
): UserDataSource {

    private val userResponseMapper = UserResponseMapper()
    private val repoResponseMapper = RepoResponseMapper()

    override suspend fun getUsers(): Flow<List<User>> = flow {
        emit(userService.getUsers().map {
            userResponseMapper.map(it)
        })
    }

    override suspend fun getUser(userLogin: String): Flow<User> = flow {
        emit(userResponseMapper.map(userService.getUser(userLogin)))
    }

    override suspend fun getUserRepos(userLogin: String): Flow<List<Repo>> = flow {
        emit(userService.getUserRepos(userLogin).map {
            repoResponseMapper.map(it)
        })
    }
}