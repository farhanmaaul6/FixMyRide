package com.bangkit.fixmyrideapp.view.detailnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.bangkit.fixmyrideapp.R
import com.bangkit.fixmyrideapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        val link_url = intent.getStringExtra(LINK_URL)

        val webView : WebView = findViewById(R.id.webview)
        webView.loadUrl(link_url.toString())
    }

    companion object {
        const val LINK_URL = "link_url"
    }
}