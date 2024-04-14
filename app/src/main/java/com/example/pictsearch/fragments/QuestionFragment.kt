package com.example.pictsearch.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pictsearch.MainActivity
import com.example.pictsearch.R
import com.example.pictsearch.logic.CommentAdapter
import com.example.pictsearch.logic.FirebaseHelper
import com.example.pictsearch.model.Comment
import com.example.pictsearch.model.Question
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.android.synthetic.main.fragment_question.view.*


class QuestionFragment : Fragment() {

    private lateinit var firebaseHelper: FirebaseHelper
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var commentList: MutableList<Comment>
    private lateinit var question: Question

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        firebaseHelper = FirebaseHelper()
        commentList = mutableListOf()
        commentAdapter = CommentAdapter(commentList)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = commentAdapter

        loadComments()

        view.postButton.setOnClickListener {
            val content = commentEditText.text.toString()
            val sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("pictSharedPreferences", Context.MODE_PRIVATE)
            val userName = sharedPreferences.getString("name", "none")
            val userSurname = sharedPreferences.getString("surname", "none")

            if (content.isNotEmpty() && ::question.isInitialized) {
                firebaseHelper.addComment(question.questionId, "userId", content, userName, userSurname)
                view.commentEditText.text.clear()
            }
        }

        view.returnback5.setOnClickListener {
            (activity as MainActivity).gotoforum()
        }

        return view
    }

    private fun loadComments() {
        firebaseHelper.loadComments(question.questionId) { comments ->
            commentList.clear()
            commentList.addAll(comments)
            commentAdapter.notifyDataSetChanged()

            Log.d("QuestionFragment", "Question ID: ${question.questionId}")
            Log.d("QuestionFragment", "Comments: ${commentList.size}")
        }
    }


    companion object {
        fun newInstance(question: Question): QuestionFragment {
            val fragment = QuestionFragment()
            fragment.question = question
            return fragment
        }
    }
}
