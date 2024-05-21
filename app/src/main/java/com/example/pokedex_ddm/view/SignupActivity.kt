package com.example.pokedex_ddm.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.pokedex_ddm.databinding.ActivitySignupBinding
import com.example.pokedex_ddm.db.AppDatabase
import com.example.pokedex_ddm.db.User
import com.example.pokedex_ddm.db.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "PokedexDatabase").build()
        userDao = db.userDao()

        binding.signupButton.setOnClickListener {
            val signupUsername = binding.signupUsername.text.toString()
            val signupPassword = binding.signupPassword.text.toString()
            signupDatabase(signupUsername, signupPassword)
        }

        binding.loginRedirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signupDatabase(username: String, password: String) {
        val user = User(username = username, password = password)

        uiScope.launch {
            val id = withContext(Dispatchers.IO) {
                userDao.insertUser(user)
            }
            if (id != -1L) {
                Toast.makeText(this@SignupActivity, "Conta criada com sucesso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@SignupActivity, "Falha ao criar conta", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
