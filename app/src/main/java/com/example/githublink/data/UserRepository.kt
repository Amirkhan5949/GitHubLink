package com.example.githublink.data

import com.example.githublink.local.entity.GitUserEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserRepository {
    suspend fun getUser() : Flow<Response<List<GitUserEntity>>>
}