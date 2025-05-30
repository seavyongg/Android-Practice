package com.app.retrofit_practice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.retrofit_practice.databinding.ItemPostsBinding

class CommentsAdapter(var commentList: ArrayList<Comments>): RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(val adapterBinding: ItemPostsBinding): RecyclerView.ViewHolder(
        adapterBinding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemPostsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comments = commentList[position]
        holder.adapterBinding.txtPostId.text = comments.postId.toString()
        holder.adapterBinding.txtId.text = comments.id.toString()
        holder.adapterBinding.txtName.text = comments.name
        holder.adapterBinding.txtEmail.text = comments.email
        holder.adapterBinding.txtDescription.text = comments.description
    }
}