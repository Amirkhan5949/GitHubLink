package com.example.githublink.data

import com.example.githublink.data.networkmodel.GitUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser() : Flow<retrofit2.Response<GitUser>>
}