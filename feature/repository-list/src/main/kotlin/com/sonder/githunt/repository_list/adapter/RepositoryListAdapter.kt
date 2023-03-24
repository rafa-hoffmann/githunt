package com.sonder.githunt.repository_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.CircleCropTransformation
import com.sonder.githunt.core.model.data.Repository
import com.sonder.githunt.feature.repository_list.databinding.ItemRepositoryBinding

class RepositoryListAdapter :
    PagingDataAdapter<Repository, RepositoryListAdapter.RepositoryViewHolder>(REPOSITORY_COMPARATOR) {

    inner class RepositoryViewHolder(private val binding: ItemRepositoryBinding) :
        ViewHolder(binding.root) {

        fun bind(repo: Repository) = with(binding) {
            repositoryName.text = repo.name
            starCount.text = repo.stargazersCount.toString()
            forkCount.text = repo.forksCount.toString()


            ownerAvatar.load(repo.owner.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            ownerName.text = repo.owner.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val REPOSITORY_COMPARATOR = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository) =
                oldItem == newItem
        }
    }
}
