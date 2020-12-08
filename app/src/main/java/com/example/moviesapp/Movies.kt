package com.example.moviesapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.LikesDislikes.LikesDisItem
import com.example.moviesapp.MovieItems.MovieItem
import com.example.moviesapp.MovieItems.MovieListAdapter
import com.example.moviesapp.MovieItems.MoviesService
import com.example.moviesapp.Rest.RestEngine
import com.example.moviesapp.auth.Token
import com.example.moviesapp.helpers.ActivitiesHelper
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.card_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Movies : AppCompatActivity() {

    var listMovies:MutableList<MovieItem> = ArrayList()
    var adapter: MovieListAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val userPref = applicationContext.getSharedPreferences("user", Context.MODE_PRIVATE)
        val token = userPref.getString("token", "0")


        if (token != null) {
            getMovies(token)
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            //LLAMA A LA FUNCIÓN FILTRAR Y LE MANDA EL NOMBRE de la pelicula
            override fun afterTextChanged(editable: Editable) {
                filtrar(token.toString(), editable.toString())
            }
        })

        /*val userPref = applicationContext.getSharedPreferences("user", Context.MODE_PRIVATE)
        val token = userPref.getString("token", "0")

        val id_movie  = intent.getIntExtra("movie_id", -1)
//        getMovie(token,id_movie)
//        getComments(token, id)
        token?.let { getMovie(it, id_movie) }
        token?.let { getComments(it, id_movie) }

        initTodoRecycler()

        btnLike.setOnClickListener {
            startActivityForResult(token?.let { it1 ->
                ActivitiesHelper().openAddTodo(this, id_movie,
                        it1
                )
            }, ActivitiesHelper().OPEN_ADD_TODO_RID)
        }*/
    }


    fun ClickDetails(item:MovieItem) {
        //startActivityForResult(ActivitiesHelper().openEditTodo(this, item), ActivitiesHelper().OPEN_EDIT_TODO_RID)

        var intent = Intent(this, MovieDetails::class.java)
        intent.putExtra("movie_id", item.id)
        startActivity(intent)
    }


    private fun getMovies(token: String){
        val moviesService = RestEngine.getRestEngine().create(MoviesService::class.java)
        val result = moviesService.listMovies(token)

        result.enqueue(object: Callback<List<MovieItem>> {
            override fun onResponse(
                call: Call<List<MovieItem>>,
                response: Response<List<MovieItem>>
            ) {
                if(response.isSuccessful) {
//                    val posts = response.body()
                    for ((id, title, image, synopsis) in response.body()!!) {
                        listMovies.add(
                                MovieItem(
                                        id,title,image, synopsis
                                )
                        )
                    }
                    println(response.body()?.get(0)?.title)

                    initTodoRecycler()
                }
            }
            override fun onFailure(call: Call<List<MovieItem>>, t: Throwable) {
                println(t.message)
                println("Respondio incorrectamente")
            }
        })
    }


    fun initTodoRecycler() {
        adapter = MovieListAdapter(listMovies, this, ::ClickDetails)
        vRecyclerMovies.layoutManager = LinearLayoutManager(this)
        vRecyclerMovies.adapter = adapter
    }

    //SIRVE PARA LA BUSQUEDA DE UN PRODUCTO
    fun filtrar(token: String, texto: String) {
        var filtrarLista:MutableList<MovieItem> = ArrayList()

        val moviesService = RestEngine.getRestEngine().create(MoviesService::class.java)
        val result = moviesService.movieSeacrch(token.toString(),texto)
        result.enqueue(object: Callback<List<MovieItem>> {
            override fun onResponse(
                    call: Call<List<MovieItem>>,
                    response: Response<List<MovieItem>>
            ) {
                if(response.isSuccessful) {
//                    val posts = response.body()
                    for ((id, title, image, synopsis) in response.body()!!) {
                        filtrarLista.add(
                                MovieItem(
                                        id,title,image, synopsis
                                )
                        )
                    }
                    //println(response.body()?.get(0)?.title)
                    adapter?.filtrar(filtrarLista)
                }
            }


            override fun onFailure(call: Call<List<MovieItem>>, t: Throwable) {
                println(t.message)
                println("Respondio incorrectamente")
            }
        })



//        for (producto in listProducts) {
//            if (producto.getNameProduct().toLowerCase().contains(texto.toLowerCase())) {
//                filtrarLista.add(producto)
//            }
//        }
        //adapter?.filtrar(filtrarLista)
    }
}