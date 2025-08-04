package com.example.githublink.data

import com.example.githublink.data.remote.DataSource
import com.example.githublink.local.dao.GitUserDao
import com.example.githublink.local.entity.GitUserEntity
import com.example.githublink.local.mapper.GitRepoEntityListMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val dataSource: DataSource,
    private val gitUserDao: GitUserDao, val gitRepoEntityListMapper: GitRepoEntityListMapper,
) : UserRepository {
    override suspend fun getUser(): Flow<Response<List<GitUserEntity>>> {
        return flow {
            val cachedData = gitUserDao.loadAllGitRepo()

            if (cachedData.isNotEmpty()) {
                emit(Response.success(cachedData))
            }

            try {
                val remoteData = dataSource.getUser().items
                val mappedData = gitRepoEntityListMapper.map(remoteData)
                gitUserDao.insertAllGitRepo(mappedData)

                val updateData = gitUserDao.loadAllGitRepo()
                emit(Response.success(updateData))

            } catch (e: IOException) {
                if (cachedData.isEmpty()) {
                    throw IOException("No internet and no cached data")
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}