package com.bangkit.fixmyrideapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bangkit.fixmyrideapp.data.utils.Constant.KEY_EMAIL
import com.bangkit.fixmyrideapp.data.utils.Constant.KEY_IS_LOGIN
import com.bangkit.fixmyrideapp.data.utils.Constant.KEY_NAME
import com.bangkit.fixmyrideapp.data.utils.Constant.KEY_TOKEN
import com.bangkit.fixmyrideapp.data.utils.Constant.KEY_USER_ID
import com.bangkit.fixmyrideapp.data.utils.SessionManager
import com.bangkit.fixmyrideapp.databinding.ActivityLoginBinding
import com.bangkit.fixmyrideapp.view.main.MainActivity
import com.bangkit.fixmyrideapp.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: SessionManager
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        sharedPref = SessionManager(this)

        binding.tvRegisterNow.setOnClickListener { toRegister() }
        binding.btnLogin.setOnClickListener { checkLogin() }

        loginViewModel.message.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        loginViewModel.isLoading.observe(this){
            showLoading(it)
        }

    }

    private fun toRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkLogin() {
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()

        if (!binding.edLoginEmail.text.isNullOrEmpty() && !binding.edLoginPassword.text.isNullOrEmpty() && password.length >= 8) {
            loginViewModel.loginUser(email, password)
            observeLoginResult()
        } else {
            Toast.makeText(this, "Password must be 8 character", Toast.LENGTH_SHORT).show()
            if (binding.edLoginEmail.text.isNullOrEmpty()){
                binding.edLoginEmail.error = "Email Tidak Boleh Kosong"
            }
            if (binding.edLoginPassword.text.isNullOrEmpty()){
                binding.edLoginPassword.error = "Password must be 8 character"
            }
        }
    }

    private fun observeLoginResult() {
        loginViewModel.loginResult.observe(this) { loginResponse ->
            if (loginResponse?.data != null) {
                val responseBody = loginResponse.data
                sharedPref.apply {
                    setBooleanPref(KEY_IS_LOGIN, true)
                    setStringPref(KEY_TOKEN, responseBody.token)
                    setStringPref(KEY_NAME, responseBody.name)
                    setStringPref(KEY_EMAIL, responseBody.email)
                }
                val i = Intent(this@LoginActivity, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)

                showToast()
            } else {
                showToast()

            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarLogin.visibility = View.VISIBLE
        } else {
            binding.progressBarLogin.visibility = View.GONE
        }
    }

    private fun showToast(){
        loginViewModel.message.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val isLogin = sharedPref.isLogin
        if (isLogin) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}