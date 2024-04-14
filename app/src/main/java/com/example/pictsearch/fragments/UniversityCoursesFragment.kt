package com.example.pictsearch.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pictsearch.MainActivity
import com.example.pictsearch.R

import kotlinx.android.synthetic.main.fragment_university_courses.view.*


class UniversityCoursesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_university_courses, container, false)

        view.ITMO.setOnClickListener {
            (activity as MainActivity).gotoitmo()
        }

        view.returnback3.setOnClickListener {
            (activity as MainActivity).makemainvisible()
        }

        return view
    }


}