package com.example.githublink.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GitUserEntity(
    @PrimaryKey (autoGenerate = true) var id : Long,val name : String,val html_Url : String
)