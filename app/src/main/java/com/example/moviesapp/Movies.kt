package com.example.moviesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.MovieItems.MovieItem
import com.example.moviesapp.MovieItems.MovieListAdapter
import com.example.moviesapp.MovieItems.MoviesService
import com.example.moviesapp.Rest.RestEngine
import kotlinx.android.synthetic.main.activity_movies.*

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class Movies : AppCompatActivity() {

    var listMovies:MutableList<MovieItem> = ArrayList()
    var adapter: MovieListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        callMoviesService()
        getTodos()
        initTodoRecycler()

    }

    fun clickComment(item:MovieItem) {
        //se tiene que abrir los comentarios
        //startActivityForResult(ActivitiesHelper().openEditTodo(this, item), ActivitiesHelper().OPEN_EDIT_TODO_RID)
    }

    fun getTodos(){
        //listMovies = todoDbController!!.getTodos()
    }
    private fun callMoviesService(){
        val moviesService = RestEngine.getRestEngine().create(MoviesService::class.java)
        val result = moviesService.listMovies()

        result.enqueue(object: retrofit2.Callback<List<MovieItem>> {
            override fun onResponse(
                call: Call<List<MovieItem>>,
                response: Response<List<MovieItem>>
            ) {
                val jsonObject = JSONObject()
                if(response.isSuccessful) {
                    println(response.body()?.get(0)?.title)


                }
            }


            override fun onFailure(call: Call<List<MovieItem>>, t: Throwable) {
                println("Respondio incorrectamente")
            }
        })
    }

    fun initTodoRecycler() {
        adapter = MovieListAdapter(listMovies, this, ::clickComment)
        vRecyclerMovies.layoutManager = LinearLayoutManager(this)
        vRecyclerMovies.adapter = adapter
    }
}