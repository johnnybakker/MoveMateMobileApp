package nl.johnny.movemate.helpers

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import java.util.Timer
import java.util.TimerTask

object TimeHelper {

    fun delay(rule: ComposeContentTestRule, ms: Long) {
        val timer = AsyncTimer(ms)
        rule.waitUntil(condition = { timer.expired }, timeoutMillis = ms + 1000)
    }

    class AsyncTimer(delay: Long) : TimerTask() {

        var expired = false

        init {
            Timer().schedule(this, delay)
        }

        override fun run() {
            expired = true
        }
    }
}