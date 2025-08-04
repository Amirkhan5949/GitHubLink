package com.example.githublink.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githublink.local.dao.GitUserDao
import com.example.githublink.local.entity.GitUserEntity

@Database(entities = [GitUserEntity::class], version = 1, exportSchema = false)
abstract class GitUserDataBase : RoomDatabase() {
    abstract fun gitUserRepoDao(): GitUserDao
}