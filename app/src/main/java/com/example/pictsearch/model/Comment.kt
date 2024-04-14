package com.example.pictsearch.model

data class Comment(
    val commentId: String = "",
    val questionId: String = "",
    val userId: String = "",
    val content: String = "",
    val timestamp: Long = 0L,
    val userName: String? = null,
    val userSurname: String? = null
)
