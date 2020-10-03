package com.example.pruebasithec

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter(private val mContext: Context, private val listaPosts: List<Post>): ArrayAdapter<Post>(mContext, 0, listaPosts) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_post, parent, false)

        val post = listaPosts[position]

        layout.title.text = post.title
        layout.body.text = post.body
        layout.postId.text = "${post.id}"
        layout.by.text = "By: ${post.nombreUsuario}"
        return layout
    }
}