package com.example.shado.litup

import android.content.Intent
import android.test.mock.MockContext
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthProvider
import com.nhaarman.mockito_kotlin.*
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Created by Jorren on 8/12/2018.
 */
class RegisterUnitTest {

    lateinit var activity : RegisterActivity
    val context = MockContext()

    @Before
    fun setup(){
        activity = spy(RegisterActivity())
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
/*
    @Test
    fun createNewUserSuccesCallback(){
        doAnswer {
            val task : Task<AuthResult> =
            return@doAnswer task
        }.whenever(activity.auth).createUserWithEmailAndPassword("newUser@mail.com","Avalid!Pass123")


        activity.createUser("newUser@mail.com", "Avalid!Pass123")


        verify(activity).startActivity(Intent(context, MainActivity::class.java))
    }*/
}