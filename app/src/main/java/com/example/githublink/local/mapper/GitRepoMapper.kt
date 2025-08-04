package com.example.githublink.local.mapper

import com.example.githublink.core.ListMapper
import com.example.githublink.core.Mapper
import com.example.githublink.data.networkmodel.GitUserResponse
import com.example.githublink.local.entity.GitUserEntity
import javax.inject.Inject

class GitRepoEntityMapper @Inject constructor() : Mapper<GitUserResponse, GitUserEntity> {
    override fun map(input: GitUserResponse): GitUserEntity {
        return GitUserEntity(id = input.id, name = input.name, html_Url = input.html_url)
    }
}

class GitRepoEntityListMapper @Inject constructor(private val gitRepoEntityMapper: GitRepoEntityMapper) :
    ListMapper<GitUserResponse, GitUserEntity> {
    override fun map(input: List<GitUserResponse>): List<GitUserEntity> {
        return input.map { gitRepoEntityMapper.map(it) }
    }
}