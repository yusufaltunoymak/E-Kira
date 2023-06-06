package com.example.splash

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.splash.api.RestApiService
import com.example.splash.api.SessionManager
import com.example.splash.api.models.ApiResponse
import com.example.splash.api.models.LoginRes
import com.example.splash.api.models.User
import com.example.splash.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        binding.apply {
            eKira.animate().setDuration(1000).alpha(0f).withEndAction{
                val sm = SessionManager(this@SplashActivity)
                val token = sm.fetchAuthToken()
                var isLogged = false
                if(token != null && token.length > 5) {
                    val apiService = RestApiService(this@SplashActivity)
                    apiService.checkAuth() {
                        if (it?.isSuccess == true) {
                            println(it.result)
                            println(it.result?.javaClass?.name)
                            val user = it.result
                            if (user != null) {
                                isLogged = true
                                mainAct(user)
                            }
                        }
                    }
                }

                if(!isLogged) {
                    registerAct()
                }
            }
        }

        setContentView(binding.root)
    }
    

    private fun registerAct() {
        val intent = Intent(this@SplashActivity, RegisterActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun mainAct(user: User) {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.putExtra("firstName", user.firstName)
        intent.putExtra("lastName", user.lastName)
        intent.putExtra("email", user.email)
        intent.putExtra("phone", user.phoneNumber)
        intent.putExtra("registerDate", user.registerDate)
        intent.putExtra("id", user.id)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}