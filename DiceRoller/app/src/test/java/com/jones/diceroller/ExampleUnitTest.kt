package com.jones.diceroller

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun generates_number() {
        val dice = Dice(6)
        val rollResult = dice.roll()
        assertTrue("The value of roll result was not between 0 and 7",
            rollResult in 1..6)
    }
}