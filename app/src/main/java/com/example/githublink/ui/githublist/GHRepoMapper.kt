package com.example.githublink.ui.githublist

import com.example.githublink.core.ListMapper
import com.example.githublink.core.Mapper
import com.example.githublink.data.networkmodel.GitUserResponse
import com.example.githublink.local.entity.GitUserEntity
import javax.inject.Inject

class GHRepoMapper @Inject constructor() : Mapper<GitUserEntity, GitUserResponse> {
    override fun map(input: GitUserEntity): GitUserResponse {
        return GitUserResponse(
            id = input.id, name = input.name, html_url = input.html_Url
        )
    }
}

class GHRepoListMapper @Inject constructor(
    private val ghRepoMapper: GHRepoMapper,
) : ListMapper<GitUserEntity, GitUserResponse> {
    override fun map(input: List<GitUserEntity>): List<GitUserResponse> {
        return input.map { ghRepoMapper.map(it) }
    }
}