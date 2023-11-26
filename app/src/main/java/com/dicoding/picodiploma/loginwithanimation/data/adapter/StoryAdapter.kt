package com.dicoding.picodiploma.loginwithanimation.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ItemHeroBinding

@Suppress("DEPRECATION")
class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.StoryViewHolder>(DiffCallback) {

    private var storyClickListener: storyClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
//        val currentStory = getItem(position)
//        holder.bind(currentStory)
        val data = getItem(position)
        if(data != null){
            holder.bind(data)
        }
    }
    fun setStoryClickListener(listener: storyClick) {
        storyClickListener = listener
    }

    inner class StoryViewHolder(private val binding: ItemHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    getItem(position)?.let { it1 -> storyClickListener?.onStoryClick(it1) }
                }
            }
        }

        fun bind(story: ListStoryItem) {
            binding.tvDicoding.text = story.name
            binding.tvDesc.text = story.description
            Glide.with(itemView.context)
                .load(story.photoUrl)
                .into(binding.ivHero)
        }
    }
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface storyClick {
    fun onStoryClick(story: ListStoryItem)
}
