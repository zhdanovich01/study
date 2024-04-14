package com.example.pictsearch.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.pictsearch.MainActivity
import com.example.pictsearch.R
import kotlinx.android.synthetic.main.fragment_fav.view.*
import kotlinx.android.synthetic.main.fragment_subjects.view.*


class FavFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fav, container, false)

        sharedPreferences = requireContext().getSharedPreferences("pictSharedPreferences", Context.MODE_PRIVATE)



        val addressesSet = sharedPreferences.getStringSet("favCourses", HashSet()) ?: HashSet()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, addressesSet.toList())
        view.listViewParks.adapter = adapter

        view.returnback7.setOnClickListener {
            (activity as MainActivity).makemainvisible()
        }


        return view
    }


}