package com.example.pictsearch.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pictsearch.R
import com.example.pictsearch.logic.StepikApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CourseCountFragment : Fragment() {

    private lateinit var textViewCourseCount: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_course_count, container, false)

        textViewCourseCount = view.findViewById(R.id.textViewCourseCount)

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("pictSharedPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("accessToken", "none") ?: "none"

        if (token == "none"){
            textViewCourseCount.text = "Ошибка"
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
                            textViewCourseCount.text = "Количество курсов: $courseCount"
                        }
                    } else {
                        // Обновление UI в главном потоке
                        CoroutineScope(Dispatchers.Main).launch {
                            textViewCourseCount.text = "Ошибка при получении количества курсов"
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()

                    // Обновление UI в главном потоке
                    CoroutineScope(Dispatchers.Main).launch {
                        textViewCourseCount.text = "Ошибка при получении количества курсов"
                    }
                }
            }
        }


        return view
    }
}
