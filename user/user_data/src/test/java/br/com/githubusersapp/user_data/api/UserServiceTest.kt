package br.com.githubusersapp.user_data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.githubusersapp.user_data.BaseServiceTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class UserServiceTest : BaseServiceTest() {
    private lateinit var service: UserService

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setup() {
        super.setup()
        service =
            Retrofit.Builder()
                .baseUrl(mockWebServer.url(""))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
    }

    @Test
    fun requestUsers_confirmResponse() {
        runBlocking {
            val resultResponse = service.getUsers()
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(3, resultResponse.size)
            Assert.assertEquals("/users", request.path)
        }
    }

    @Test
    fun requestUser_confirmResponse() {
        runBlocking {
            val resultResponse = service.getUser("torvalds")
            val request = mockWebServer.takeRequest()

            Assert.assertEquals("Linus Torvalds", resultResponse.name)
            Assert.assertEquals("/users/torvalds", request.path)
        }
    }

    @Test
    fun requestUserRepos_confirmResponse() {
        runBlocking {
            val resultResponse = service.getUserRepos("torvalds")
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(2, resultResponse.size)
            Assert.assertEquals("/users/torvalds/repos", request.path)
        }
    }
}