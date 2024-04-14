package com.example.pictsearch.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pictsearch.MainActivity
import com.example.pictsearch.R
import com.example.pictsearch.logic.NotificationReceiver
import kotlinx.android.synthetic.main.fragment_p_m_i.view.*

import kotlinx.android.synthetic.main.fragment_person.*
import kotlinx.android.synthetic.main.fragment_person.view.*
import kotlinx.android.synthetic.main.fragment_person.view.name
import java.util.*


class PersonFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_person, container, false)

        val sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("pictSharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        var getname = sharedPreferences.getString("name", "none")
        var getsurname = sharedPreferences.getString("surname", "none")
        var getmail = sharedPreferences.getString("mail", "none")
        var fullname = getname + getsurname
        view.name.text = fullname.toString()
        view.changename.text = getname.toString()
        view.mail2.text = getmail.toString()

        view.connectstepikbut.setOnClickListener {
            (activity as MainActivity).gotoconnectstepik()
        }

        view.connectnotif.setOnClickListener {
            if (notifon.visibility == View.VISIBLE){
                setDailyNotification(requireContext())
                view.notifon.visibility = View.GONE
                view.notifoff.visibility = View.VISIBLE
            }else{

                cancelDailyNotification(requireContext())
                view.notifon.visibility = View.VISIBLE
                view.notifoff.visibility = View.GONE
            }

        }

        view.fav1.setOnClickListener{

        }

        view.fav2.setOnClickListener{

        }

        view.fav3.setOnClickListener{

        }

        view.fav4.setOnClickListener{

        }

        view.fav5.setOnClickListener{

        }

        return view
    }

    fun setDailyNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.action = "SEND_NOTIFICATION"
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Устанавливаем уведомление на каждый день в определенное время
        // Здесь установлено на 9:00 утра
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelDailyNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }


}