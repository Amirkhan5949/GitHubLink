package com.example.githublink.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githublink.core.state.ApiState
import com.example.githublink.databinding.ActivityGitUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GitUserActivity : ComponentActivity() {
    private lateinit var rv: RecyclerView
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGitUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel.getUser()

        observerApiState()

        rv = binding.ghRepoList
        with(rv) {
            layoutManager = LinearLayoutManager(context)

        }
    }

    private fun observerApiState() {
        lifecycleScope.launch {
            userViewModel.apiState.collect { state ->
                when (state) {
                    ApiState.Loading -> {}
                    ApiState.Success -> {
                        rv.adapter = GitUserAdapter(userViewModel.userList)
                    }
                    is ApiState.Error -> state.error
                }
            }
        }
    }
}