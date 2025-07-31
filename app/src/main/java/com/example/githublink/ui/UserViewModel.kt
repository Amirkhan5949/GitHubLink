package com.example.githublink.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githublink.core.state.ApiState
import com.example.githublink.core.state.Result
import com.example.githublink.core.state.asResult
import com.example.githublink.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {

    private val _apiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val apiState : StateFlow<ApiState> = _apiState.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            repository.getUser().asResult().collect { result->
                when(result){
                    is Result.Loading -> _apiState.emit(ApiState.Loading)
                    is Result.Error -> _apiState.emit(ApiState.Error(result.exception?.message.orEmpty()))
                    is Result.Success<*> -> _apiState.emit(ApiState.Success)
                }
            }
        }
    }
}