package com.example.githublink.data

import com.example.githublink.data.networkmodel.GitUser
import com.example.githublink.data.remote.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(private val dataSource: DataSource) : UserRepository {
    override suspend fun getUser(): Flow<Response<GitUser>> {
       return flow {
           emit(dataSource.getUser())
       }.flowOn(Dispatchers.IO)
    }
}