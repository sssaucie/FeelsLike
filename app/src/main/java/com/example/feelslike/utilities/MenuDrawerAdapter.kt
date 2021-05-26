package com.example.feelslike.utilities

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.feelslike.R
import com.example.feelslike.view_model.MenuDrawerViewModel

class MenuDrawerAdapter(private var items : ArrayList<MenuDrawerViewModel>,
                        private var currentPos : Int) :
    RecyclerView.Adapter<MenuDrawerAdapter.NavigationItemViewHolder>()
{
    private lateinit var context : Context

    class NavigationItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationItemViewHolder
    {
        context = parent.context
        val menuItem = LayoutInflater.from(parent.context).inflate(
            R.layout.menu_drawer_row, parent, false)
        return NavigationItemViewHolder(menuItem)
    }

    override fun getItemCount(): Int
    {
        return items.count()
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int)
    {
        // Background color highlights based on selection
        if (position == currentPos)
        {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_grey))
        }
        else
        {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.clear))
        }
//        holder.itemView.navigation_icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
//        holder.itemView.navigation_title.setTextColor(Color.WHITE)
//        val font = ResourcesCompat.getFont(context, R.font.font_family)
//        holder.itemView.navigation_text.typeface = font
//        holder.itemView.navigation_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
//        holder.itemView.navigation_title.text = items[position].title
//        holder.itemView.navigation_icon.setImageResource(items[position].icon)
    }
}