package com.example.pictsearch.model



data class Question(
    val questionId: String = "",
    val content: String = "",
    val userId: String = "",
    val timestamp: Long = 0,
    var answerCount: Int = 0,
    val userName: String? = null,
    val userSurname: String? = null
) {
    // Пустой конструктор без аргументов
    constructor() : this("", "", "", 0, 0, null, null)
}


