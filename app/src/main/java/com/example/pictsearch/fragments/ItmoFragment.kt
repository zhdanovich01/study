package com.example.pictsearch.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pictsearch.MainActivity
import com.example.pictsearch.R
import kotlinx.android.synthetic.main.fragment_itmo.view.*



class ItmoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_itmo, container, false)

        view.returnback4.setOnClickListener {
            (activity as MainActivity).gotouniversity()
        }

        view.theme1.setOnClickListener {
            (activity as MainActivity).gotopmifragment()
        }

        return view
    }


}