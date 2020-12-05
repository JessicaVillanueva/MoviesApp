package com.example.moviesapp.MovieItems

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_movie.view.*
import java.util.*

class MovieListAdapter (var items:MutableList<MovieItem>, val context: Context, val onClickComment:(item:MovieItem) -> Unit): RecyclerView.Adapter<MovieListAdapter.MovieHolder>(){

    class MovieHolder(val itemTemplate: View): RecyclerView.ViewHolder(itemTemplate) {
        fun render(item:MovieItem, context: Context, onClickEdit:(item:MovieItem) -> Unit) {
            /* Datos del template */
            itemTemplate.txtTitleMovie.text = item.title
            itemTemplate.txtSynopsisMovie.text = item.synopsis
            if(!item.image.isEmpty()){
                Picasso.get().load(item.image).into(itemTemplate.imageMovie)
            }
            /* Button Listeners */
            itemTemplate.btnComments.setOnClickListener {
                onClickEdit(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieHolder(layoutInflater.inflate(R.layout.card_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.render(items[position], context, onClickComment)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun filtrar(filtroMovies: MutableList<MovieItem>) {
        this.items = filtroMovies
        notifyDataSetChanged()
    }
}