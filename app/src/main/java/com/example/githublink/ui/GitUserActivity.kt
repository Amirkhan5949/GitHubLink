package com.example.githublink.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.githublink.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GitUserActivity : ComponentActivity() {
    private val userViewModel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userViewModel.getUser()

    }
}