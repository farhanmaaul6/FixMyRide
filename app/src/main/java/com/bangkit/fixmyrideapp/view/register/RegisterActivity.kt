package com.bangkit.fixmyrideapp.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bangkit.fixmyrideapp.R
import com.bangkit.fixmyrideapp.databinding.ActivityRegisterBinding
import com.bangkit.fixmyrideapp.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        setupViewModel()
        setupView()

    }

    private fun setupViewModel() {
        registerViewModel.isLoading.observe(this){
            showLoading(it)
        }

        registerViewModel.regisResult.observe(this){
            if (it == "User created") {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                showToast()
            }else{
                showToast()
            }
        }
    }

    private fun setupView() {
        binding.tvLoginNow.setOnClickListener { toLogin() }
        binding.btnRegister.setOnClickListener { checkRegister() }
    }

    private fun checkRegister() {
        val name = binding.edRegisterUsername.text.toString()
        val email = binding.edRegisterEmail.text.toString()
        val password = binding.edRegisterPassword.text.toString()

        if (!binding.edRegisterUsername.text.isNullOrEmpty() && !binding.edRegisterEmail.text.isNullOrEmpty() && password.length >= 8){
            showLoading(true)
            registerViewModel.registerUser(name, email, password)
        } else{
            Toast.makeText(this, "Password must be 8 character", Toast.LENGTH_SHORT).show()
            if (binding.edRegisterUsername.text.isNullOrEmpty()){
                binding.edRegisterUsername.error = "Nama Tidak boleh Kosong"
            }
            if (binding.edRegisterEmail.text.isNullOrEmpty()){
                binding.edRegisterEmail.error = "Email Tidak Boleh Kosong"
            }
            if (binding.edRegisterPassword.text.isNullOrEmpty()){
                binding.edRegisterPassword.error = "Password must be 8 character"
            }
        }
    }

    private fun toLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarRegister.visibility = View.VISIBLE
        } else {
            binding.progressBarRegister.visibility = View.GONE
        }
    }

    private fun showToast(){
        registerViewModel.regisResult.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}