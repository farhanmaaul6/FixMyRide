package com.bangkit.fixmyrideapp.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bangkit.fixmyrideapp.R
import com.bangkit.fixmyrideapp.data.api.ApiService
import com.bangkit.fixmyrideapp.data.repository.ProfileRepository
import com.bangkit.fixmyrideapp.data.utils.Result
import com.bangkit.fixmyrideapp.data.utils.SessionManager
import com.bangkit.fixmyrideapp.databinding.ActivityDetailBinding
import com.bangkit.fixmyrideapp.databinding.ActivityProfileBinding
import com.bangkit.fixmyrideapp.view.SettingsActivity
import com.bangkit.fixmyrideapp.view.about.AboutActivity
import com.bangkit.fixmyrideapp.view.login.LoginActivity
import com.bangkit.fixmyrideapp.view.login.LoginViewModel
import java.net.URL

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModel.SearchFoodRecipeFactory(this)
    }

    private var email: String? = null
    private var username: String? = null
    private var token: String? = null
    private lateinit var sharedPref: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SessionManager(this)
        email = sharedPref.getEmail
        username = sharedPref.getUsername
        token

        binding.tvFullName.text = username
        binding.tvEmail.text = email

        getDataUser()
        binding.ivPfp.setOnClickListener {
            val intent = Intent(this, UpdateProfileActivity::class.java)
            startActivity(intent)
        }
        getSettings()
        getAbout()
        logOut()
    }

    private fun getDataUser() {
        profileViewModel.getDataDetail(email.toString()).observe(this){
            when(it){
                is Result.Loading -> {}
                is Result.Error -> {}
                is Result.Success -> {
                    val response = it.data
                    binding.tvEmail.text = response.email
                    binding.tvFullName.text = response.name
                    Glide.with(this)
                        .load(response.image_url)
                        .placeholder(R.drawable.baseline_account_circle_24)
                        .into(binding.ivPfp)
                }
            }
        }
    }


    private fun getSettings(){
        binding.btnSettings.setOnClickListener{
            intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getAbout(){
        binding.btnAbout.setOnClickListener{
            intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logOut() {
        binding.btnLogout.setOnClickListener {
            sharedPref.clearData()
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}