package com.example.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.splash.api.RestApiService
import com.example.splash.api.SessionManager
import com.example.splash.databinding.ActivityMainBinding
import com.stripe.android.PaymentConfiguration
import com.vmadalin.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Main)
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this,R.id.navHost)
        setupWithNavController(binding.bottomNavigationView,navController)

        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51MtVLtAyz4OGmsxH10w2wgNW3tEOCVFHuWV9FQpGWNVMou9OkBR7t78w8XoPXUG0XpdlTgR2vohSCIodCxHWbnH700eNeNhGSp"
        )
    }

    fun logout(view : View) {
        var sm = SessionManager(this@MainActivity)
        val apiService = RestApiService(this@MainActivity)
        apiService.logoutAuth() {
            if (it?.isSuccess == true && it?.result == "OK") {
                sm.saveAuthToken("")
                val intent = Intent(this@MainActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                sm.saveAuthToken("")
            }
        }
        // Start RegisterActivity here
        val intent = Intent(this@MainActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }



}