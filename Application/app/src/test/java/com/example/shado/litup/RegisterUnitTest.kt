package com.example.shado.litup

import org.junit.Test

/**
 * Created by Jorren on 8/12/2018.
 */
class RegisterUnitTest {
    @Test
    fun equalPassCorrect(){
        val activity = RegisterActivity();

        var result = activity.checkEqualPass("testPass", "testPass")

        AssertEqual
    }
}