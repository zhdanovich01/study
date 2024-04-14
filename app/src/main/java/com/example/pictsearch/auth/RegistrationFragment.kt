package com.example.pictsearch.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.neweco.model.User
import com.example.pictsearch.MainActivity
import com.example.pictsearch.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_registration.view.*


class RegistrationFragment : Fragment() {

    private lateinit var database : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_registration, container, false)


        val sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("pictSharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        view.nextbut1.setOnClickListener{
            var mail: String = view.mail333.text.toString()
            if (mail != ""){

                view.step1.visibility = View.GONE
                val slideUpAnimation: Animation =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left)


                view.step1.startAnimation(slideUpAnimation)
                var newmail = ""
                for (i in mail.toString()){
                    if (i == '@'){
                        newmail += ' '
                    }
                    else if(i == '.'){
                        newmail += ' '
                    }
                    else{
                        newmail += i
                    }
                }
                mail = newmail
                editor.putString("mail", mail).apply()
                view.step2.visibility = View.VISIBLE
                view.nextbut1.visibility = View.GONE
                view.nextbut2.visibility = View.VISIBLE

            }else {
                Toast.makeText(getActivity(), "Вы не ввели почту!", Toast.LENGTH_SHORT).show();
            }
        }
        view.nextbut2.setOnClickListener{

            var name: String = view.name333.text.toString()
            var surname: String = view.surname333.text.toString()
            var lastname: String = view.lastname333.text.toString()
            if (name != "" && surname != "" && lastname != ""){
                view.step2.visibility = View.GONE
                val slideUpAnimation: Animation =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left)


                view.step2.startAnimation(slideUpAnimation)

                editor.putString("name", name).apply()
                editor.putString("lastname", lastname).apply()
                editor.putString("surname", surname).apply()
                view.step3.visibility = View.VISIBLE
                view.nextbut2.visibility = View.GONE
                view.sendinf.visibility = View.VISIBLE
            }else {
                Toast.makeText(getActivity(), "Вы оставили поле имя, фамилия или отчество пустым!", Toast.LENGTH_SHORT).show();
            }
        }
        view.sendinf.setOnClickListener{
            var pass: String = view.pass.text.toString()
            var pass1: String = view.pass1.text.toString()
            var mail = sharedPreferences.getString("mail", "none")
            var name = sharedPreferences.getString("name", "none")
            var lastname = sharedPreferences.getString("lastname", "none")
            var surname = sharedPreferences.getString("surname", "none")
            if (pass != ""){
                if (pass == pass1){
                    database = FirebaseDatabase.getInstance().getReference("users")
                    val User = User(mail.toString(), pass.toString(), name.toString(), surname.toString(), lastname.toString())
                    database.child(mail.toString()).setValue(User).addOnSuccessListener {
                        editor.putString("log", "yes").apply()
                        Toast.makeText(getActivity(), "Вы успешно зарегестрировались!", Toast.LENGTH_LONG).show()
                        val slideUpAnimation: Animation =
                            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left)


                        view.startAnimation(slideUpAnimation)
                        view.visibility = View.GONE
                        (activity as MainActivity).makemainvisible()

                    }.addOnFailureListener {
                        Toast.makeText(getActivity(), "Произошла ошибка.", Toast.LENGTH_LONG).show()
                    }

                }
                else{
                    Toast.makeText(getActivity(), "Введенные Вами пароли не совпадают!", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(getActivity(), "Вы не ввели пароль!", Toast.LENGTH_SHORT).show();
            }
        }


        return view
    }



}