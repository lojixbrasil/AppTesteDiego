package com.example.apptestediego.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apptestediego.R
import com.example.apptestediego.model.PostsResponseModel

class PostsAdapter(var listPosts: List<PostsResponseModel>) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return listPosts.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems( listPosts[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems( post: PostsResponseModel) {
            val tvItem = itemView.findViewById<TextView>(R.id.tvItem)
            tvItem.setText("UserID: ${post.userId} \nId: ${post.id} \n${post.title} \n${post.body} \n")

        }
    }
}