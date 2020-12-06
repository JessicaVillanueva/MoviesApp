package com.example.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.MovieItems.MovieItem
import com.example.moviesapp.MovieItems.MovieListAdapter
import com.example.moviesapp.Rest.RestEngine
import com.example.moviesapp.comments.CommentItem
import com.example.moviesapp.comments.CommentListAdapter
import com.example.moviesapp.comments.CommentService
import kotlinx.android.synthetic.main.activity_movie_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetails : AppCompatActivity() {
    var listComments:MutableList<CommentItem> = ArrayList()
    var adapter: CommentListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        val movie_id  = intent.getIntExtra("movie_id", -1)
        getComments(movie_id)

    }

    private fun getComments(movieId:Int){
        val commentService = RestEngine.getRestEngine().create(CommentService::class.java)
        val result = commentService.listCommentsForMovie(movieId)

        result.enqueue(object: Callback<List<CommentItem>>{
            override fun onResponse(
                call: Call<List<CommentItem>>,
                response: Response<List<CommentItem>>
            ) {
                if(response.isSuccessful){
                    for((id, comment, date, movie_id, user_id, username)in response.body()!!){
                        listComments.add(
                            CommentItem(id, comment, date, movie_id, user_id, username)
                        )
                    }
                    initCommentRecycler()

                }
            }

            override fun onFailure(call: Call<List<CommentItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun clickComment(item:CommentItem){

    }

    fun initCommentRecycler() {
        adapter = CommentListAdapter(listComments, this, ::clickComment)
        rvComments.layoutManager = LinearLayoutManager(this)
        rvComments.adapter = adapter
    }


}