package com.example.githublink.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githublink.data.networkmodel.GitUserResponse
import com.example.githublink.databinding.ActivityGitUserItemBinding

class GitUserAdapter(private val gitRepoList: MutableList<GitUserResponse>) :
    RecyclerView.Adapter<GitUserAdapter.GitUserViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GitUserViewHolder {
        val binding =
            ActivityGitUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GitUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GitUserViewHolder, position: Int) {
        val user = gitRepoList[position]
        holder.tvId.text = user.id
        holder.tvName.text = user.name
        holder.tvUrl.text = user.html_url
    }

    override fun getItemCount(): Int = gitRepoList.size

    class GitUserViewHolder(private val binding: ActivityGitUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvId = binding.tvId
        val tvUrl = binding.tvUrl
        val tvName = binding.tvName
    }

}