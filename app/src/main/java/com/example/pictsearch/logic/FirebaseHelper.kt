package com.example.pictsearch.logic

import android.util.Log
import com.example.pictsearch.model.Comment
import com.example.pictsearch.model.Question
import com.google.firebase.database.*
import java.util.*

class FirebaseHelper {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun postQuestion(userId: String, content: String, userName: String?, userSurname: String?) {
        val questionId = database.child("questions").push().key
        val timestamp = System.currentTimeMillis()

        val question = Question(questionId!!, content, userId, timestamp, 0, userName, userSurname)

        database.child("questions").child(questionId).setValue(question)
    }


    fun addComment(questionId: String, userId: String, content: String, userName: String?, userSurname: String?) {
        val commentId = database.child("comments").push().key ?: return
        val comment = Comment(commentId, questionId, userId, content, System.currentTimeMillis(), userName, userSurname)
        database.child("comments").child(commentId).setValue(comment)
    }


    fun loadQuestions(callback: (List<Question>) -> Unit) {
        database.child("questions").get().addOnSuccessListener { snapshot ->
            val questions = mutableListOf<Question>()
            snapshot.children.forEach { data ->
                val question = data.getValue(Question::class.java)
                question?.let { questions.add(it) }
            }
            callback(questions)
        }.addOnFailureListener {
            callback(emptyList())
        }
    }

    fun loadQuestionWithAnswerCount(callback: (List<Question>) -> Unit) {
        database.child("questions").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val questions = mutableListOf<Question>()
                snapshot.children.forEach { questionSnapshot ->
                    val question = questionSnapshot.getValue(Question::class.java)
                    question?.let {
                        loadAnswerCountForQuestion(it.questionId) { count ->
                            it.answerCount = count
                            questions.add(it)
                            if (questions.size == snapshot.childrenCount.toInt()) {
                                callback(questions)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseHelper", "Failed to load questions: ${error.message}")
                callback(emptyList())
            }
        })
    }

    private fun loadAnswerCountForQuestion(questionId: String, callback: (Int) -> Unit) {
        database.child("comments").orderByChild("questionId").equalTo(questionId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    callback(snapshot.childrenCount.toInt())
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseHelper", "Failed to load answer count: ${error.message}")
                    callback(0)
                }
            })
    }


    fun loadComments(questionId: String, callback: (List<Comment>) -> Unit) {
        database.child("comments").orderByChild("questionId").equalTo(questionId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val comments = mutableListOf<Comment>()
                    snapshot.children.forEach { data ->
                        val comment = data.getValue(Comment::class.java)
                        comment?.let { comments.add(it) }
                    }
                    callback(comments)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseHelper", "Failed to load comments: ${error.message}")
                    callback(emptyList())
                }
            })
    }




}
