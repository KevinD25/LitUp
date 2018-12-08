package com.example.shado.litup

import org.junit.Test
import org.junit.Assert.*

/**
 * Created by Jorren on 8/12/2018.
 */
class RegisterUnitTest {
    @Test
    fun equalPassCorrect(){
        val activity = RegisterActivity()

        var result = activity.checkEqualPass("testPass", "testPass")

        assertEquals(true, result)
    }

    @Test
    fun equalPassIncorrect(){
        val activity = RegisterActivity()

        var result = activity.checkEqualPass("TestPass", "testPass")

        assertEquals(false, result)
    }
}