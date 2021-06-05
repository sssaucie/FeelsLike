package com.example.feelslike.utilities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feelslike.databinding.ListItemFavoritesBinding
import com.example.feelslike.model.entity.CalculationsEntity

//class RecyclerViewFavoritesAdapter(var favorite : TaskList) : RecyclerView.Adapter<ListSelectionViewHolder>()
//{
//    val latLng = getItem(position)
//    override fun onCreateViewHolder(
//        parent: ViewGroup, viewType: Int): ListSelectionViewHolder
//    {
//        val binding = ListItemFavoritesBinding.inflate(
//            LayoutInflater.from(parent.context), parent, false)
//        return ListSelectionViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(
//        holder: ListSelectionViewHolder, position: Int)
//    {
//        TODO("Not yet implemented")
//    }
//
//    override fun getItemCount(): Int
//    {
//        return latLng.size
//    }
//}
//
//class ListSelectionViewHolder(val binding : ListItemFavoritesBinding
//) : RecyclerView.ViewHolder(binding.root)
//{
//
//}
//class RecyclerViewFavoritesAdapter(val clickListener : FavoritesListClickListener
//) : ListAdapter<CalculationsEntity, RecyclerViewFavoritesAdapter
//            .FavoritesListViewHolder>(FavoritesListDiffCallback())
//{
//    override fun onBindViewHolder(
//        holder: RecyclerViewFavoritesAdapter
//        .FavoritesListViewHolder, position: Int)
//    {
//        /**
//         * Get element from dataset at this position and replace the
//         * contents of the view with that element.
//         */
//
//        val latLng = getItem(position)
//        holder.bind(clickListener, latLng)
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup, viewType: Int) : FavoritesListViewHolder
//    {
//        // Create a new view, which defines the UI of the list item.
//
//        return FavoritesListViewHolder.from(parent)
//    }
//
//    /**
//     * Provide a reference to the type of views that you are using
//     * (custom ViewHolder)
//     */
//
//    class FavoritesListViewHolder private constructor(
//        val binding : ListItemFavoritesBinding)
//        : RecyclerView.ViewHolder(binding.root)
//    {
//            fun bind(clickListener : FavoritesListClickListener, item : CalculationsEntity)
//            {
//                binding.favoritePlace = item
//                binding.clickListener = clickListener
//                binding.executePendingBindings()
//            }
//
//        companion object
//        {
//            fun from(parent: ViewGroup) : FavoritesListViewHolder
//            {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = ListItemFavoritesBinding.inflate(
//                    layoutInflater, parent, false)
//                return FavoritesListViewHolder(binding)
//            }
//        }
//        }
//}
//
//class FavoritesListDiffCallback : DiffUtil.ItemCallback<CalculationsEntity>()
//{
//    override fun areItemsTheSame(
//        oldItem: CalculationsEntity,
//        newItem: CalculationsEntity
//    ): Boolean {
//        return oldItem.calculations_id == newItem.calculations_id
//    }
//
//    override fun areContentsTheSame(
//        oldItem: CalculationsEntity,
//        newItem: CalculationsEntity
//    ): Boolean {
//        return oldItem == newItem
//    }
//}
//
//class FavoritesListClickListener(val clickListener : (favorite : CalculationsEntity) -> Unit)
//{
//    fun onClick(favorite : CalculationsEntity) = clickListener(favorite)
//}