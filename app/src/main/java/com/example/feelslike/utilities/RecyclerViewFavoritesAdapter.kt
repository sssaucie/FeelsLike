package com.example.feelslike.utilities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feelslike.databinding.ListItemFavoritesBinding

class RecyclerViewFavoritesAdapter(private val clickListener : FavoritesClickListener) :
    ListAdapter<FeelsLikeRepository,
            RecyclerViewFavoritesAdapter.FavoritesViewHolder>(FavoritesDiffCallback())
{
    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int)
    {
        val favorite = getItem(position)
        holder.bind(favorite, clickListener)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): FavoritesViewHolder
    {
        return FavoritesViewHolder.from(parent)
    }

    class FavoritesViewHolder private constructor(val binding : ListItemFavoritesBinding
    ) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(item: FeelsLikeRepository, clickListener: FavoritesClickListener)
        {
            binding.favoritePlace = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object
        {
            fun from(parent : ViewGroup) : FavoritesViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemFavoritesBinding.inflate(
                    layoutInflater, parent, false)
                return FavoritesViewHolder(binding)
            }
        }
    }
}

class FavoritesDiffCallback : DiffUtil.ItemCallback<FeelsLikeRepository>()
{
    override fun areItemsTheSame(
        oldItem: FeelsLikeRepository,
        newItem: FeelsLikeRepository
    ): Boolean
    {
        return oldItem.allFavorites == newItem.allFavorites
    }

    override fun areContentsTheSame(
        oldItem: FeelsLikeRepository,
        newItem: FeelsLikeRepository
    ): Boolean {
        return oldItem == newItem
    }
}

class FavoritesClickListener(val clickListener : (favorite : FeelsLikeRepository) -> Unit)
{
    fun onClick(favorite : FeelsLikeRepository) = clickListener(favorite)
}