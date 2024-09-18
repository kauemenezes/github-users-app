package br.com.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This test class generates a basic startup baseline profile for the target package.
 *
 * We recommend you start with this but add important user flows to the profile to improve their performance.
 * Refer to the [baseline profile documentation](https://d.android.com/topic/performance/baselineprofiles)
 * for more information.
 *
 * You can run the generator with the "Generate Baseline Profile" run configuration in Android Studio or
 * the equivalent `generateBaselineProfile` gradle task:
 * ```
 * ./gradlew :app:generateReleaseBaselineProfile
 * ```
 * The run configuration runs the Gradle task and applies filtering to run only the generators.
 *
 * Check [documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args)
 * for more information about available instrumentation arguments.
 *
 * After you run the generator, you can verify the improvements running the [StartupBenchmarks] benchmark.
 *
 * When using this class to generate a baseline profile, only API 33+ or rooted API 28+ are supported.
 *
 * The minimum required version of androidx.benchmark to generate a baseline profile is 1.2.0.
 **/
@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() {
        // This example works only with the variant with application id `br.com.githubusersapp`."
        rule.collect(
            packageName = "br.com.githubusersapp",

            includeInStartupProfile = true
        ) {

            // Start default activity for your app
            pressHome()
            startActivityAndWait()

            // 1. Wait until the content is asynchronously loaded
            waitForAsyncContent()
            // 2. Scroll the user list
            scrollUserList()
            // 3. Navigate to user details screen
            goToUserDetailsScreen()

            // Check UiAutomator documentation for more information how to interact with the app.
            // https://d.android.com/training/testing/other-components/ui-automator
        }
    }
}

fun MacrobenchmarkScope.waitForAsyncContent() {
    device.wait(Until.hasObject(By.res(this.packageName, "user_list")), 10_000)
    val contentList = device.findObject(By.res("user_list"))
    contentList.wait(Until.hasObject(By.res("user_card")), 5_000)
}

fun MacrobenchmarkScope.scrollUserList() {
    val userList = device.findObject(By.res("user_list"))
    // Set gesture margin to avoid triggering gesture navigation
    userList.setGestureMargin(device.displayWidth / 5)
    userList.fling(Direction.DOWN, 7000)
    userList.fling(Direction.UP, 7000)
    device.waitForIdle()
}

fun MacrobenchmarkScope.goToUserDetailsScreen() {
    val userList = device.findObject(By.res("user_list"))
    val userCards = userList.findObjects(By.res("user_card"))
    val index = (iteration ?: 0) % userCards.size
    userCards[index].click()
    // Wait until the screen is gone = the detail is shown
    device.wait(Until.gone(By.res("user_list")), 5_000)
}