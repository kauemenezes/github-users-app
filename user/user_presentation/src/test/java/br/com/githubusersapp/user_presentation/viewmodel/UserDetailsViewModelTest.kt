package br.com.githubusersapp.user_presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.testing.invoke
import app.cash.turbine.test
import br.com.githubusersapp.user_domain.model.screen.UserDetails
import br.com.githubusersapp.user_domain.repository.UserRepository
import br.com.githubusersapp.user_domain.usecase.GetUserDetailsUseCase
import br.com.githubusersapp.user_presentation.sample.userRepos
import br.com.githubusersapp.user_presentation.userdetails.UserDetailsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserDetailsViewModelTest {
    private lateinit var getUserDetailsUseCase: GetUserDetailsUseCase
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        userRepository = mockk<UserRepository>()
        getUserDetailsUseCase = GetUserDetailsUseCase(userRepository)
    }

    @Test
    fun getUserDetails_confirmObtained() {
        runTest {
            coEvery { userRepository.getUserDetails("test1") } returns flowOf(userRepos)
            val savedState = SavedStateHandle(route = UserDetails(userLogin = "test1"))
            val viewModel =
                UserDetailsViewModel(
                    savedState,
                    getUserDetailsUseCase,
                    StandardTestDispatcher(testScheduler),
                )
            viewModel.uiState.test {
                Assert.assertEquals(false, awaitItem().isLoading)
                Assert.assertEquals(true, awaitItem().isLoading)
                Assert.assertEquals(userRepos, awaitItem().user)
                cancelAndIgnoreRemainingEvents()
            }
            coVerify { getUserDetailsUseCase("test1") }
        }
    }
}