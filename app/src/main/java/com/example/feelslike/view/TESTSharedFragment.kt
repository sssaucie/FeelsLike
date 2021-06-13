package com.example.feelslike.view

import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.feelslike.MainActivity

class TESTSharedFragment : Fragment()
{
    lateinit var btnMaps : Button

    fun onActivityCreated()
    {
        btnMaps.setOnClickListener {
            (activity as MainActivity).onOpenMap()
        }
    }
}