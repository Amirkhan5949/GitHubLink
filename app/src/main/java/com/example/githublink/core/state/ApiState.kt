package com.example.githublink.core.state

sealed interface ApiState {
    data class Error(val error : String) : ApiState
    data object Success : ApiState
    data object Loading : ApiState
}