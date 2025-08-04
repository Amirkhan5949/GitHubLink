package com.example.githublink.ui.githubdetail

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.githublink.R
import com.example.githublink.databinding.ActivityGitHubDetailBinding


class GitHubDetail : AppCompatActivity() {
    private lateinit var binding: ActivityGitHubDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGitHubDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = intent.getStringExtra(getString(R.string.repo_url)).orEmpty()
        setupWebView(url)
    }

    private fun setupWebView(url: String) = with(binding.webView) {
        binding.loader.visibility = View.VISIBLE

        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.loader.visibility = View.GONE
            }
        }
        loadUrl(url)
    }
}