package com.example.githublink.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.githublink.R
import com.example.githublink.core.state.ApiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GitUserActivity : ComponentActivity() {
    private val userViewModel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userViewModel.getUser()
        observerApiState()
    }

    private fun observerApiState() {
        lifecycleScope.launch {
            userViewModel.apiState.collect {state->
                when(state){
                     ApiState.Loading -> {}
                     ApiState.Success -> {}
                    is ApiState.Error -> state.error.toString()
                }
            }
        }
    }
}