package com.example.feelslike.utilities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feelslike.model.entity.FeelsLikeEntity
/*
class InitialUserInputAdapter(val clickListener: InitialUserInputClickListener) :
    ListAdapter<FeelsLikeEntity, InitialUserInputAdapter.InitialUserInputViewHolder>(
        InitialUserInputDiffCallback())
{
    val activitiesKeyList = ArrayList(activityMaps.keys)
    val activitiesValueList = ArrayList(activityMaps.values)
    val activities = listOf(activitiesKeyList)
    val adapter =
    override fun onBindViewHolder(
        holder: InitialUserInputAdapter.InitialUserInputViewHolder,
        position: Int)
    {
        /**
         * Get element from dataset at this position and replace the
         * contents of the view with that element.
         */
        val selectedItem = getItem(position)
        holder.bind(clickListener, selectedItem)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InitialUserInputAdapter.InitialUserInputViewHolder
    {
        // Create a new view, which defines the UI of the list item.
        return InitialUserInputViewHolder.from(parent)
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    class InitialUserInputViewHolder private constructor(
        val binding: ListItemInitialUserInputBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        fun bind(clickListener : InitialUserInputClickListener, item : FeelsLikeEntity)
        {
            binding.clothing = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object
        {
            fun from(parent : ViewGroup) : InitialUserInputViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemInitialUserInputBinding.inflate(
                    layoutInflater, parent, false)
                return InitialUserInputViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two nun-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between an
 * old list and a new list that has been passed to 'submitList'.
 */

class InitialUserInputDiffCallback : DiffUtil.ItemCallback<FeelsLikeEntity>()
{
    override fun areContentsTheSame(oldItem: FeelsLikeEntity, newItem: FeelsLikeEntity): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: FeelsLikeEntity, newItem: FeelsLikeEntity): Boolean {
        return oldItem.clothing == newItem.clothing
    }
}

class InitialUserInputClickListener(val clickListener : (selection : FeelsLikeEntity) -> Unit)
{
    fun onClick(selection : FeelsLikeEntity) = clickListener(selection)
}
*/