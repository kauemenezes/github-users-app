package br.com.githubusersapp

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.githubusersapp.user_domain.repository.UserRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class UsersInstrumentedTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun user_list_and_user_details_are_displayed_successfully() =
        runTest {
            userRepository.getUsers().collectLatest { users ->
                users.forEach {
                    composeRule.onNodeWithText(it.login).isDisplayed()
                }
            }
            composeRule.onNodeWithText("Buscar usuário").performTextInput("test3")
            composeRule.onNodeWithText("test1").isNotDisplayed()
            composeRule.onNodeWithText("test2").isNotDisplayed()
            composeRule.onNodeWithTag("test3").isDisplayed()

            // go to user details screen
            composeRule.onNodeWithTag("test3").performClick()
            composeRule.onNodeWithText("test 3").isDisplayed()
            composeRule.onNodeWithText("test3").isDisplayed()
            composeRule.onNodeWithText("repo1").isDisplayed()
        }
}