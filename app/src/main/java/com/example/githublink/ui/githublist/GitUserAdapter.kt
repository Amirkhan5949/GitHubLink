package com.example.githublink.ui.githublist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githublink.data.networkmodel.GitUserResponse
import com.example.githublink.databinding.ActivityGitUserItemBinding

class GitUserAdapter(
    private val gitRepoList: MutableList<GitUserResponse>,
    private val onItemClick: (GitUserResponse) -> Unit,
) :
    RecyclerView.Adapter<GitUserAdapter.GitUserViewHolder>() {

    fun updateList(newList: List<GitUserResponse>) {
        gitRepoList.clear()
        gitRepoList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class GitUserViewHolder(private val binding: ActivityGitUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvId = binding.tvId
        val tvUrl = binding.tvUrl
        val tvName = binding.tvName

        fun bind(user: GitUserResponse) {
            tvUrl.text = user.html_url
            tvId.text = user.id.toString()
            tvName.text = user.name
            binding.cvGhRepo.setOnClickListener {
                onItemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GitUserViewHolder {
        val binding =
            ActivityGitUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GitUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GitUserViewHolder, position: Int) {
        holder.bind(gitRepoList[position])
    }

    override fun getItemCount(): Int = gitRepoList.size


}