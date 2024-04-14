package com.example.pictsearch.logic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pictsearch.R
import com.example.pictsearch.model.Question
import java.text.SimpleDateFormat
import java.util.*

class QuestionAdapter(
    private val questionList: MutableList<Question>,
    private val onItemClick: (Question) -> Unit
) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)
        val answerCountTextView: TextView = itemView.findViewById(R.id.answerCountTextView)  // Новый TextView для количества ответов
        val userInfoTextView: TextView = itemView.findViewById(R.id.userInfoTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(questionList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return ViewHolder(view)
    }

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd.MM HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questionList[position]
        holder.contentTextView.text = question.content
        holder.timestampTextView.text = formatTimestamp(question.timestamp)
        holder.userInfoTextView.text = "${question.userName} ${question.userSurname}"  // Отображение имени и фамилии пользователя
        holder.answerCountTextView.text = "Ответов: ${question.answerCount}"
    }



    override fun getItemCount(): Int {
        return questionList.size
    }
}
