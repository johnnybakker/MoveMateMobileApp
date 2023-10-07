package nl.johnny.movemate.utils

import org.junit.Assert
import org.junit.Test

class TimeUtilTest {

    @Test
    fun `Test seconds to time string` () {

        Assert.assertEquals(
            TimeUtil.secondsToTimeString(3600 + 60 + 1),
            "01:01:01")


    }

}