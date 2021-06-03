package com.example.feelslike.utilities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feelslike.databinding.ListItemFavoritesBinding
import com.example.feelslike.model.entity.CalculationsEntity

class FavoritesAdapter(val clickListener : FavoritesListClickListener
) : ListAdapter<CalculationsEntity, FavoritesAdapter
            .FavoritesListViewHolder>(FavoritesListDiffCallback())
{
    override fun onBindViewHolder(
        holder: FavoritesAdapter
        .FavoritesListViewHolder, position: Int)
    {
        /**
         * Get element from dataset at this position and replace the
         * contents of the view with that element.
         */

        val latLng = getItem(position)
        holder.bind(clickListener, latLng)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int) : FavoritesListViewHolder
    {
        // Create a new view, which defines the UI of the list item.

        return FavoritesListViewHolder.from(parent)
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */

    class FavoritesListViewHolder private constructor(
        val binding : ListItemFavoritesBinding)
        : RecyclerView.ViewHolder(binding.root)
    {
            fun bind(clickListener : FavoritesListClickListener, item : CalculationsEntity)
            {
                binding.favoritePlace = item
                binding.clickListener = clickListener
                binding.executePendingBindings()
            }

        companion object
        {
            fun from(parent: ViewGroup) : FavoritesListViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemFavoritesBinding.inflate(
                    layoutInflater, parent, false)
                return FavoritesListViewHolder(binding)
            }
        }
        }
}

class FavoritesListDiffCallback : DiffUtil.ItemCallback<CalculationsEntity>()
{
    override fun areItemsTheSame(
        oldItem: CalculationsEntity,
        newItem: CalculationsEntity
    ): Boolean {
        return oldItem.calculations_id == newItem.calculations_id
    }

    override fun areContentsTheSame(
        oldItem: CalculationsEntity,
        newItem: CalculationsEntity
    ): Boolean {
        return oldItem == newItem
    }
}

class FavoritesListClickListener(val clickListener : (favorite : CalculationsEntity) -> Unit)
{
    fun onClick(favorite : CalculationsEntity) = clickListener(favorite)
}