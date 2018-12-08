package com.example.shado.litup

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Created by Jorren on 8/12/2018.
 */
class RegisterUnitTest {

    lateinit var activity : RegisterActivity

    @Before
    fun setup(){
        activity = RegisterActivity()
    }
    @Test
    fun equalPassCorrect(){
        var result = activity.checkEqualPass("testPass", "testPass")

        assertEquals(true, result)
    }

    @Test
    fun equalPassIncorrect(){
        var result = activity.checkEqualPass("TestPass", "testPass")

        assertEquals(false, result)
    }

    @Test
    fun validatePasswordCorrect(){
        var correctPasses = arrayListOf<String>("Avalid!Pass123", "other65V@lid", "6Va!LID=", "8Long!P@")

        correctPasses.forEach { s ->
            var result = activity.checkPassValid("Avalid!Pass123")
            assertTrue(result)
        }
    }

    @Test
    fun validatePasswordIncorrect(){
        var correctPasses = arrayListOf<String>("No!Pa3", "unvalidPass!", "veryunvalidpass", "123456789", "otherUnvalidPass")

        correctPasses.forEach { s ->
            var result = activity.checkPassValid("Avalid!Pass123")
            assertTrue(result)
        }
    }
}