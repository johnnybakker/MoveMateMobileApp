package nl.johnny.movemate.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nl.johnny.movemate.helpers.TimeHelper
import nl.johnny.movemate.ui.screens.WorkoutScreen
import nl.johnny.movemate.ui.screens.WorkoutScreenTestTags
import nl.johnny.movemate.ui.theme.MoveMateTheme
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testWorkoutScreen() {

        val time = "00:00:00"
        val distance = "10 KM"
        val speed = "0 KM/H"


        rule.setContent {
            MoveMateTheme {
                var started by remember { mutableStateOf(false) }

                WorkoutScreen(time, distance, speed, started) {
                    started = !started
                }
            }
        }

        rule.onNodeWithTag(WorkoutScreenTestTags.TimeText).assertIsDisplayed().assertTextContains(time)
        rule.onNodeWithTag(WorkoutScreenTestTags.SpeedText).assertIsDisplayed().assertTextEquals(speed)
        rule.onNodeWithTag(WorkoutScreenTestTags.DistanceText).assertIsDisplayed().assertTextEquals(distance)
        rule.onNodeWithTag(WorkoutScreenTestTags.StopButton).assertDoesNotExist()
        TimeHelper.delay(rule, 1000)
        rule.onNodeWithTag(WorkoutScreenTestTags.StartButton)
            .assertIsDisplayed()
            .performClick()
            .assertDoesNotExist()
        TimeHelper.delay(rule, 1000)
        rule.onNodeWithTag(WorkoutScreenTestTags.StopButton).assertIsDisplayed()
    }
}