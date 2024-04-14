package com.example.pictsearch.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pictsearch.R

import com.example.pictsearch.logic.FirebaseHelper
import com.example.pictsearch.logic.QuestionAdapter
import com.example.pictsearch.model.Question
import kotlinx.android.synthetic.main.fragment_forum.view.*

class ForumFragment : Fragment() {

    private lateinit var firebaseHelper: FirebaseHelper
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var questionList: MutableList<Question>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forum, container, false)

        firebaseHelper = FirebaseHelper()
        questionList = mutableListOf()

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)


        view.postButton.setOnClickListener {
            val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("pictSharedPreferences", Context.MODE_PRIVATE)

            val getname = sharedPreferences.getString("name", "none").toString()
            val getsurname = sharedPreferences.getString("surname", "none").toString()



            val content = view.questionEditText.text.toString()
            firebaseHelper.postQuestion("userId", content, getname, getsurname)

            view.questionEditText.text.clear()
        }

        loadQuestions()

        return view
    }




    private fun loadQuestions() {

        firebaseHelper.loadQuestionWithAnswerCount { questions ->
            questionList.clear()
            questionList.addAll(questions)
            questionAdapter.notifyDataSetChanged()
        }

        firebaseHelper.loadQuestions { questions ->
            questionList.clear()
            questionList.addAll(questions)

            questionAdapter = QuestionAdapter(questionList) { question ->
                val fragment = QuestionFragment.newInstance(question)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.allframes, fragment)
                    .addToBackStack(null)
                    .commit()
            }

            val recyclerView: RecyclerView = view?.findViewById(R.id.recyclerView) ?: return@loadQuestions
            recyclerView.adapter = questionAdapter
        }
    }

}
