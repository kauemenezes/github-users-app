package br.com.githubusersapp.user_data.repository

import br.com.githubusersapp.user_data.api.UserService
import br.com.githubusersapp.user_data.datasource.UserDataSource
import br.com.githubusersapp.user_data.datasource.UserDataSourceImpl
import br.com.githubusersapp.user_data.sample.userReposResponse
import br.com.githubusersapp.user_data.sample.userResponse
import br.com.githubusersapp.user_data.sample.usersResponse
import br.com.githubusersapp.user_domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {
    private lateinit var userDataSource: UserDataSource
    private lateinit var userRepository: UserRepository
    private var mockkUserService = mockk<UserService>()

    @Before
    fun setup() {
        userDataSource = UserDataSourceImpl(mockkUserService)
        userRepository = UserRepositoryImpl(userDataSource, UnconfinedTestDispatcher())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getUsers_confirmObtained() =
        runBlocking {
            coEvery { mockkUserService.getUsers() } returns usersResponse
            userRepository.getUsers().take(1).collect {
                Assert.assertEquals(3, it.size)
                Assert.assertEquals("test1", it[0].login)
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun getUserDetails_confirmObtained() =
        runBlocking {
            coEvery { mockkUserService.getUser("test1") } returns userResponse
            coEvery { mockkUserService.getUserRepos("test1") } returns userReposResponse
            userRepository.getUserDetails("test1").take(1).collect {
                Assert.assertEquals("test1", it.user.login)
                Assert.assertEquals(3, it.userRepos.size)
            }
        }
}