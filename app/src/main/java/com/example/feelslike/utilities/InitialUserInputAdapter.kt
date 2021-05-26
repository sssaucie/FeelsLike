package com.example.feelslike.utilities

/*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feelslike.R
import com.example.feelslike.model.entity.UserEntity

class InitialUserInputAdapter  : ListAdapter<UserEntity, InitialUserInputAdapter.InitialUserInputViewHolder>(
    InitialUserInputComparator())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InitialUserInputViewHolder {
        return InitialUserInputViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: InitialUserInputViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.first_name)
        holder.bind(current.last_name)
        holder.bind(current.email_address)
        holder.bind(current.height.toString())
        holder.bind(current.weight.toString())
    }

    class InitialUserInputViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        private val userItemView : TextView = itemView.findViewById(R.id.initialUserInputFragment)

        fun bind(text : String?)
        {
            userItemView.text = text
        }

        companion object
        {
            fun create(parent: ViewGroup) : InitialUserInputViewHolder
            {
                val view : View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_initial_user_input, parent, false)
                return InitialUserInputViewHolder(view)
            }
        }
    }

    class InitialUserInputComparator : DiffUtil.ItemCallback<UserEntity>()
    {
        override fun areItemsTheSame(
            oldItem: UserEntity,
            newItem: UserEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: UserEntity,
            newItem: UserEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}

 */