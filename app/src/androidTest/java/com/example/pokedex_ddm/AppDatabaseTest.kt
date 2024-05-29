package com.example.pokedex_ddm

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokedex_ddm.db.AppDatabase
import com.example.pokedex_ddm.db.User
import com.example.pokedex_ddm.db.UserDao
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeUserAndReadInList() = runBlocking {
        val user = User(1, "userTest", "12345")
        userDao.insertUser(user)
        val fetchedUser = userDao.getUser("userTest", "12345")
        TestCase.assertNotNull(fetchedUser)
        TestCase.assertEquals(user.username, fetchedUser?.username)
        TestCase.assertEquals(user.password, fetchedUser?.password)
    }
}