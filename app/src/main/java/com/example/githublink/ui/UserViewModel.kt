package com.example.githublink.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githublink.core.EMPTY
import com.example.githublink.core.state.ApiState
import com.example.githublink.core.state.Result
import com.example.githublink.core.state.asResult
import com.example.githublink.data.UserRepository
import com.example.githublink.data.networkmodel.GitUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {

    private val _apiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val apiState: StateFlow<ApiState> = _apiState.asStateFlow()

    private val _searchQuery = MutableStateFlow(String.EMPTY)
    fun search(searchText: String) {
        _searchQuery.value = searchText
    }

    private val _searchGitRepo = MutableStateFlow<List<GitUserResponse>>(emptyList())
    val searhGitRepo: StateFlow<List<GitUserResponse>> = _searchGitRepo.asStateFlow()


    val allUserList = mutableListOf<GitUserResponse>()

    init {
        observeSearch()
    }

    fun getUser() {
        viewModelScope.launch {
            repository.getUser().asResult().collect { result ->
                when (result) {
                    is Result.Loading -> _apiState.emit(ApiState.Loading)
                    is Result.Error -> _apiState.emit(ApiState.Error(result.exception?.message.orEmpty()))
                    is Result.Success -> {
                        _apiState.emit(ApiState.Success)
                        val users = result.data
                        allUserList.addAll(users.items)
                        _searchGitRepo.value = users.items
                    }
                }
            }
        }
    }

    private fun observeSearch() {
        viewModelScope.launch {
            _searchQuery.debounce(300).distinctUntilChanged().collectLatest { query ->
                _searchGitRepo.value = if (query.isBlank()) {
                    allUserList
                } else {
                    allUserList.filter {
                        it.name.contains(query, ignoreCase = true) || it.id
                            .contains(query)
                    }
                }
            }
        }
    }
}