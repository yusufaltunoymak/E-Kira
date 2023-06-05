package com.example.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.splash.api.RestApiService
import com.example.splash.api.SessionManager
import com.example.splash.api.models.LoginPost
import com.example.splash.api.models.LoginRes
import com.example.splash.api.models.RegisterPost
import com.example.splash.api.models.RegisterRes
import com.example.splash.api.models.User
import com.example.splash.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Main)
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun signInClicked(view: View) {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this@RegisterActivity, "Enter email and password", Toast.LENGTH_LONG)
                .show()
        }

        val apiService = RestApiService(this@RegisterActivity)
        val data = LoginPost(  email = email.toString(),
            password = password.toString() )

        apiService.loginPost(data) {
            if (it?.isSuccess == true) {
                if(it.result != null) {
                    val sm = SessionManager(this@RegisterActivity)
                    val loginResult = it?.result as LoginRes
                    sm.saveAuthToken(loginResult.accessToken)
                    val fullName = ""
                    if(loginResult.user.firstName.isNotEmpty()) fullName.plus(loginResult.user.firstName)
                    if(loginResult.user.lastName.isNotEmpty()) fullName.plus(" ").plus(loginResult.user.lastName)
                    var str: String = "Hoş Geldiniz, %s".format(
                        fullName,
                    )
                    Toast.makeText(this@RegisterActivity, str, Toast.LENGTH_LONG).show()
                    startActivityUserData(loginResult.user)
                }
            } else {
                Toast.makeText(this@RegisterActivity, it?.error.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signUpClicked(view: View) {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this@RegisterActivity, "Enter email and password", Toast.LENGTH_LONG)
                .show()
        } else {
            val apiService = RestApiService(this@RegisterActivity)
            val data = RegisterPost(  email = email.toString(),
                password = password.toString() )

            apiService.registerPost(data) {
                if (it?.isSuccess == true) {
                    val sm = SessionManager(this@RegisterActivity)
                    val registerResult = it.result as RegisterRes
                    sm.saveAuthToken(registerResult.accessToken)
                    Toast.makeText(this@RegisterActivity, "Hoş Geldiniz", Toast.LENGTH_LONG).show()
                    startActivityUserData(registerResult.user)
                } else {
                    Toast.makeText(this@RegisterActivity, it?.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun startActivityUserData(user: User) {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        intent.putExtra("firstName", user.firstName)
        intent.putExtra("lastName", user.lastName)
        intent.putExtra("email", user.email)
        intent.putExtra("phone", user.phoneNumber)
        intent.putExtra("registerDate", user.registerDate)
        intent.putExtra("id", user.id)
        startActivity(intent)
        finish()
    }
}


