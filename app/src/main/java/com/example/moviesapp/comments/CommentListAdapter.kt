package com.example.moviesapp.comments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.MovieItems.MovieItem
import com.example.moviesapp.MovieItems.MovieListAdapter
import com.example.moviesapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_comment.view.*
import kotlinx.android.synthetic.main.card_movie.view.*

class CommentListAdapter(var items:MutableList<CommentItem>, val context:Context, val onClickComment:(item: CommentItem) -> Unit): RecyclerView.Adapter<CommentListAdapter.CommentHolder>() {
    class CommentHolder (val itemTemplate: View): RecyclerView.ViewHolder(itemTemplate){
        fun render(item:CommentItem, context: Context, onClickComment: (item: CommentItem) -> Unit){
            /* Datos del template */
            itemTemplate.txtNameUser.text = "@" + item.username
            itemTemplate.txtComment.text = item.comment
            itemTemplate.txtDate.text = item.date

            /* Button Listeners */
            itemTemplate.LayoutCardComment.setOnClickListener {
                onClickComment(item)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListAdapter.CommentHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CommentListAdapter.CommentHolder(
            layoutInflater.inflate(
                R.layout.card_comment,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentListAdapter.CommentHolder, position: Int) {
        holder.render(items[position], context, onClickComment)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}