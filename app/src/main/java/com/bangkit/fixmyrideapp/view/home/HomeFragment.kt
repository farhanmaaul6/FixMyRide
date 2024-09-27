package com.bangkit.fixmyrideapp.view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.fixmyrideapp.R
import com.bangkit.fixmyrideapp.databinding.FragmentHomeBinding
import com.bangkit.fixmyrideapp.view.maps.MapsActivity
import com.bangkit.fixmyrideapp.view.profile.ProfileActivity
import com.google.android.gms.location.FusedLocationProviderClient

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var lastClickTime: Long = 0
    private val doubleClickTimeThreshold = 1000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        topNavMenu()
        searchButton()
    }

    private fun searchButton() {
        binding.btnCircle.setOnClickListener {
            val clickTime = System.currentTimeMillis()

            if (clickTime - lastClickTime < doubleClickTimeThreshold) {
                navigateToMaps()
            }

            lastClickTime = clickTime
        }
    }

    private fun navigateToMaps() {
        val intent = Intent(requireActivity(), MapsActivity::class.java)
        startActivity(intent)
    }

    private fun topNavMenu() {
        binding.ivProfile.setOnClickListener {
            val intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}