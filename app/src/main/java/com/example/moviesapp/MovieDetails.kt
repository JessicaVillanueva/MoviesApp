package com.example.moviesapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.MovieItems.MovieItem
import com.example.moviesapp.MovieItems.MoviesService
import com.example.moviesapp.Rest.RestEngine
import com.example.moviesapp.comments.Comment
import com.example.moviesapp.comments.CommentItem
import com.example.moviesapp.comments.CommentListAdapter
import com.example.moviesapp.comments.CommentService
import com.example.moviesapp.helpers.ActivitiesHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetails : AppCompatActivity() {
    var listComments:MutableList<CommentItem> = ArrayList()
    var adapter: CommentListAdapter? = null
    var comment:Comment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val userPref = applicationContext.getSharedPreferences("user", Context.MODE_PRIVATE)
        val token = userPref.getString("token", "0")

        val id_movie  = intent.getIntExtra("movie_id", -1)
//        getMovie(token,id_movie)
//        getComments(token, id)
        token?.let { getMovie(it, id_movie) }
        token?.let { getComments(it, id_movie) }

        initCommentRecycler()

        btnAddComment.setOnClickListener {
            startActivityForResult(token?.let { it1 ->
                ActivitiesHelper().openAddTodo(this, id_movie,
                    it1
                )
            }, ActivitiesHelper().OPEN_ADD_TODO_RID)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) { // Si no falla
            if (requestCode == ActivitiesHelper().OPEN_ADD_TODO_RID) {

                val id_movie= data?.getIntExtra("IDMOVIE", 0)
                val commentario= data?.getStringExtra("COMMENT")
                val token = data?.getStringExtra("TOKEN")

                comment =Comment(commentario, id_movie, 1)

                val commentService = RestEngine.getRestEngine().create(CommentService::class.java)
                val result = commentService.saveComment(token, comment)

                result.enqueue(object : Callback<CommentItem?> {
                    override fun onFailure(call: Call<CommentItem?>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                    override fun onResponse(
                        call: Call<CommentItem?>,
                        response: Response<CommentItem?>
                    ) {
                        if(response.isSuccessful){
                            Toast.makeText(
                                this@MovieDetails,
                                "Comentario agregado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            if (token != null) {
                                if (id_movie != null) {
                                    getComments(token, id_movie)

                                    adapter?.notifyDataSetChanged()
                                }
                            }
                        }else{
                            Toast.makeText(
                                this@MovieDetails,
                                response.message(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                })


            } else if (requestCode == ActivitiesHelper().OPEN_EDIT_TODO_RID) {
                /*var elemento = todoItems?.find { it.id == data?.getIntExtra("ID", -1)!!}
                elemento?.title = data?.getStringExtra("TITLE")!!
                elemento?.message = data?.getStringExtra("MESSAGE")!!
                elemento?.date = data?.getStringExtra("DATE")!!
                elemento?.imageUri = data?.getStringExtra("IMAGE_URI")!!*/
            }
            //adapter?.notifyDataSetChanged()
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            Toast.makeText(this@MovieDetails, "Ocurrio un error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMovie(token: String, movieId: Int){
        val moviesService = RestEngine.getRestEngine().create(MoviesService::class.java)
        val result = moviesService.movieDetails(token, movieId)
        result.enqueue(object: Callback<MovieItem>{
            override fun onFailure(call: Call<MovieItem>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<MovieItem>, response: Response<MovieItem>) {
                if(response.isSuccessful){
                    txtTitleMovie.text = response.body()?.title
                    //txtTitleMovie.setText(response.body()?.title)
                    txtSynopsisMovie.setText(response.body()?.synopsis)
                    Picasso.get().load(response.body()?.image).into(imageMovie)
                }
            }
        })
    }

    private fun getComments(token:String, movieId:Int){
        val getlistComments:MutableList<CommentItem> = ArrayList()

        val commentService = RestEngine.getRestEngine().create(CommentService::class.java)
        val result = commentService.listCommentsForMovie(token, movieId)

        result.enqueue(object: Callback<List<CommentItem>>{
            override fun onResponse(
                call: Call<List<CommentItem>>,
                response: Response<List<CommentItem>>
            ) {
                if(response.isSuccessful){
                    for((id, comment, date, movie_id, user_id, username)in response.body()!!){
                        getlistComments.add(
                            CommentItem(id, comment, date, movie_id, user_id, username)
                        )
                    }
                    listComments = getlistComments
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
