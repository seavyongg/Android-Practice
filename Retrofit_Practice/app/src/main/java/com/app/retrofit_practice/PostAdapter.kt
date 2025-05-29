package com.app.retrofit_practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.retrofit_practice.databinding.ActivityRetrivedDataBinding
import com.app.retrofit_practice.databinding.ItemPostsBinding
import java.util.ArrayList

class PostAdapter(var postList: ArrayList<Posts>): RecyclerView.Adapter<PostAdapter.PostsViewHolder>() {
    inner class PostsViewHolder(val adapterBinding: ItemPostsBinding ): RecyclerView.ViewHolder(adapterBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = ItemPostsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostsViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return postList.size
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
       val posts = postList[position]
         holder.adapterBinding.txtUserId.text = posts.userId.toString()
            holder.adapterBinding.txtId.text = posts.id.toString()
            holder.adapterBinding.txtTitle.text = posts.title
            holder.adapterBinding.txtBody.text = posts.subTitle
    }
}