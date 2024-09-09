package br.com.githubusersapp.user_presentation.viewmodel

import app.cash.turbine.test
import br.com.githubusersapp.user_domain.repository.UserRepository
import br.com.githubusersapp.user_domain.usecase.GetUsersUseCase
import br.com.githubusersapp.user_presentation.sample.users
import br.com.githubusersapp.user_presentation.users.UsersViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {
    private lateinit var getUsersUseCase: GetUsersUseCase
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = mockk<UserRepository>()
        getUsersUseCase = GetUsersUseCase(userRepository)
    }

    @Test
    fun getUsers_confirmObtained() {
        runTest {
            coEvery { userRepository.getUsers() } returns flowOf(users)
            val viewModel =
                UsersViewModel(getUsersUseCase, UnconfinedTestDispatcher(testScheduler))
            viewModel.uiState.test {
                Assert.assertEquals(true, awaitItem().isLoading)
                Assert.assertEquals(users, awaitItem().users)
                viewModel.onSearchTextChange("test1")
                Assert.assertEquals(1, awaitItem().users.size)
                cancelAndIgnoreRemainingEvents()
            }
            coVerify { getUsersUseCase() }
        }
    }
}