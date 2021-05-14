package com.example.feelslike.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.feelslike.R


class InitialUserInputFragment : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val binding : ViewDataBinding? = DataBindingUtil.inflate(
            inflater, R.layout.activity_initial_user_input, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = com.example.feelslike.MainActivity()

        return binding?.root
    }
}