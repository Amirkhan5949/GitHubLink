package com.example.githublink.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githublink.local.entity.GitUserEntity

@Dao
interface GitUserDao {
    @Insert
    fun insertGitRepo(gitRepo: GitUserEntity)

    @Update
    fun updateGitRepo(gitRepo: GitUserEntity)

    @Delete
    fun deleteGitRepo(gitRepo: GitUserEntity)

    @Insert
    fun insertAllGitRepo(vararg gitRepo: GitUserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGitRepo(user: List<GitUserEntity>)

    @Query("Select * from GitUserEntity")
    suspend fun loadAllGitRepo(): List<GitUserEntity>
}