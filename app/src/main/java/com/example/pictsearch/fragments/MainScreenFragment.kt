package com.example.pictsearch.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pictsearch.MainActivity
import com.example.pictsearch.R
import com.example.pictsearch.logic.StepikApiService
import kotlinx.android.synthetic.main.fragment_main_screen.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainScreenFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_screen, container, false)

        view.subjects.setOnClickListener {
            (activity as MainActivity).gotosubjects()
        }

        view.notes.setOnClickListener {
            (activity as MainActivity).gotonotes()

        }

        view.unicourses.setOnClickListener {
            (activity as MainActivity).gotouniversity()
        }

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("pictSharedPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("accessToken", "none") ?: "none"

        if (token == "none"){
            view.textView6.text = "Ошибка"
        }
        else{
            // Создание Retrofit клиента
            val retrofit = Retrofit.Builder()
                .baseUrl("https://stepik.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Создание экземпляра API сервиса
            val apiService = retrofit.create(StepikApiService::class.java)

            // Выполнение запроса в отдельном потоке
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.getSocialAccounts("Bearer $token", "YOUR_USER_ID").execute()

                    if (response.isSuccessful) {
                        val socialAccounts = response.body()?.socialAccounts
                        val courseCount = socialAccounts?.size ?: 0

                        // Обновление UI в главном потоке
                        CoroutineScope(Dispatchers.Main).launch {
                            view.textView6.text = "$courseCount"
                        }
                    } else {
                        // Обновление UI в главном потоке
                        CoroutineScope(Dispatchers.Main).launch {
                            view.textView6.text = "Ошибка"
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()

                    // Обновление UI в главном потоке
                    CoroutineScope(Dispatchers.Main).launch {
                        view.textView6.text = "Ошибка"
                    }
                }
            }
        }

        return view
    }


}