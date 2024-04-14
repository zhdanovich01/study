package com.example.pictsearch.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pictsearch.MainActivity
import com.example.pictsearch.R
import kotlinx.android.synthetic.main.fragment_p_m_i.*
import kotlinx.android.synthetic.main.fragment_p_m_i.view.*


class PMIFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_p_m_i, container, false)

        val sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("pictSharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        view.returnback6.setOnClickListener {
            (activity as MainActivity).gotoitmo()
        }



        view.gotheme1.setOnClickListener {
            openLink("https://start.itmo.ru/ktu/security_8")
            val addressesSet = sharedPreferences.getStringSet("favCourses", HashSet()) ?: HashSet()
            addressesSet.add(namepmi1.text.toString())

            editor.putStringSet("favCourses", addressesSet)
            editor.apply()
        }

        view.gotheme2.setOnClickListener {
            openLink("https://start.itmo.ru/pythonnew1")
            val addressesSet = sharedPreferences.getStringSet("favCourses", HashSet()) ?: HashSet()
            addressesSet.add(namepmi2.text.toString())

            editor.putStringSet("favCourses", addressesSet)
            editor.apply()
        }

        view.gotheme3.setOnClickListener {
            openLink("https://start.itmo.ru/pk_pypro20")
            val addressesSet = sharedPreferences.getStringSet("favCourses", HashSet()) ?: HashSet()
            addressesSet.add(namepmi3.text.toString())

            editor.putStringSet("favCourses", addressesSet)
            editor.apply()
        }

        view.gotheme4.setOnClickListener {
            openLink("https://start.itmo.ru/pk_parametry")
            val addressesSet = sharedPreferences.getStringSet("favCourses", HashSet()) ?: HashSet()
            addressesSet.add(namepmi4.text.toString())

            editor.putStringSet("favCourses", addressesSet)
            editor.apply()
        }

        view.gotheme5.setOnClickListener {
            openLink("https://start.itmo.ru/aip/ege2")
            val addressesSet = sharedPreferences.getStringSet("favCourses", HashSet()) ?: HashSet()
            addressesSet.add(namepmi5.text.toString())

            editor.putStringSet("favCourses", addressesSet)
            editor.apply()
        }

        return view
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}