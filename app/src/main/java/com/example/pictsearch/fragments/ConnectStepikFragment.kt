package com.example.pictsearch.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.pictsearch.MainActivity
import com.example.pictsearch.R
import com.github.kittinunf.fuel.core.HttpException
import com.github.kittinunf.fuel.httpPost
import kotlinx.android.synthetic.main.fragment_connect_stepik.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


class ConnectStepikFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_connect_stepik, container, false)




        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("pictSharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        view.btnLogin.setOnClickListener {
            //val clientId = "fhtulG1m6ioe3eVN2TNHkchLS8JzhGAeQgrMH1Vr"
            //val clientSecret = "H5ja4sh5auTQR7JSRxvt77tkoJV8uK2KSc7gBPeigLENkBeaMkGaypRlmAxP6xq3ctKDZf1PxHfm5MrFWJyrolCnmv4p3NhbGFcHXRfW6icrQxlxpFczo8X94QrW7z93"

            val clientId = view.clientid.text.toString()
            val clientSecret = view.clientsecret.text.toString()

            //val clientId = "vzh555555@yandex.ru"
            //val clientSecret = "Almaz777"

            CoroutineScope(Dispatchers.Main).launch {
                val accessToken = getAccessToken(clientId, clientSecret)
                if (accessToken != null){
                    editor.putString("accessToken", accessToken.toString()).apply()
                    Toast.makeText(getActivity(), accessToken, Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(getActivity(), "Ошибка получения токена!", Toast.LENGTH_LONG).show()
                }

            }

            view.returnback.setOnClickListener {
                (activity as MainActivity).gotoprofile()
            }

        }



        return view
    }

    private suspend fun getAccessToken(clientId: String, clientSecret: String): String? {
        val authString = "$clientId:$clientSecret"
        val base64Auth = android.util.Base64.encodeToString(authString.toByteArray(), android.util.Base64.NO_WRAP)

        return withContext(Dispatchers.IO) {
            try {
                val (_, response, result) = "https://stepik.org/oauth2/token/"
                    .httpPost(listOf("grant_type" to "client_credentials"))
                    .header("Authorization" to "Basic $base64Auth")
                    .responseString()

                when (response.statusCode) {
                    in 200..299 -> {
                        val jsonResponse = JSONObject(result.get())
                        jsonResponse.optString("access_token", null)
                    }
                    else -> {
                        null
                    }
                }
            } catch (e: HttpException) {
                null
            } catch (e: Exception) {
                null
            }
        }
    }

}