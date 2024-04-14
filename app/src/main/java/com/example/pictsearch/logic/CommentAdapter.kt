package com.example.pictsearch.logic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pictsearch.R
import com.example.pictsearch.model.Comment
import java.text.SimpleDateFormat
import java.util.*

class CommentAdapter(private val commentList: List<Comment>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView2)
        val userNameTextView: TextView = itemView.findViewById(R.id.userInfoTextView)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView2)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentList[position]
        holder.contentTextView.text = comment.content
        holder.userNameTextView.text = "${comment.userName} ${comment.userSurname}"
        holder.timestampTextView.text = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(comment.timestamp))
    }


    override fun getItemCount(): Int {
        return commentList.size
    }
}
