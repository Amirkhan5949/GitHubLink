package com.example.githublink.data.remote

import com.example.githublink.data.networkmodel.GitUser
import retrofit2.http.GET

interface DataSource {
    @GET("repositories?q=language:swift&sort=stars&order=desc")
    suspend fun getUser(): retrofit2.Response<GitUser>
}