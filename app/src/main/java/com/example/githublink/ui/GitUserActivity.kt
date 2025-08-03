package com.example.githublink.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githublink.R
import com.example.githublink.core.onTextChanged
import com.example.githublink.core.state.ApiState
import com.example.githublink.databinding.ActivityGitUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GitUserActivity : ComponentActivity() {
    private lateinit var rv: RecyclerView
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var adapter: GitUserAdapter
    private lateinit var showErrorMsg: TextView
    private lateinit var loader: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGitUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel.getUser()
        showErrorMsg = binding.errorMassage
        loader = binding.loader
        binding.etSearch.onTextChanged { query ->
            userViewModel.search(query)
        }

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
                    ApiState.Loading -> {
                        loader.visibility = View.VISIBLE
                    }

                    ApiState.Success -> {
                        loader.visibility = View.GONE
                        showErrorMsg.visibility = View.GONE
                        adapter = GitUserAdapter(userViewModel.searhGitRepo.value.toMutableList())
                        rv.adapter = adapter
                        observeSearchResults()
                    }

                    is ApiState.Error -> {
                        loader.visibility = View.GONE
                        showErrorMsg.visibility = View.VISIBLE
                        showErrorMsg.text = state.error

                    }
                }
            }
        }
    }

    private fun observeSearchResults() {
        lifecycleScope.launch {
            userViewModel.searhGitRepo.collectLatest { list ->
                if (list.isNotEmpty()) {
                    showErrorMsg.visibility = View.GONE
                    rv.visibility = View.VISIBLE
                    adapter.updateList(list)
                    adapter.notifyDataSetChanged()
                } else {
                    showErrorMsg.visibility = View.VISIBLE
                    rv.visibility = View.GONE
                    showErrorMsg.text = getString(R.string.no_data_found)
                }
            }
        }
    }
}