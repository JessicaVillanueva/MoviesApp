package com.example.moviesapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.MovieItems.MovieItem
import com.example.moviesapp.MovieItems.MovieListAdapter
import com.example.moviesapp.MovieItems.MoviesService
import com.example.moviesapp.Rest.RestEngine
import kotlinx.android.synthetic.main.activity_movies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Movies : AppCompatActivity() {

    var listMovies:MutableList<MovieItem> = ArrayList()
    var adapter: MovieListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        getMovies()

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            //LLAMA A LA FUNCIÓN FILTRAR Y LE MANDA EL NOMBRE de la pelicula
            override fun afterTextChanged(editable: Editable) {
                filtrar(editable.toString())
            }
        })
    }

    fun clickComment(item:MovieItem) {
        //se tiene que abrir los comentarios
        //startActivityForResult(ActivitiesHelper().openEditTodo(this, item), ActivitiesHelper().OPEN_EDIT_TODO_RID)
    }

    private fun getMovies(){
        val moviesService = RestEngine.getRestEngine().create(MoviesService::class.java)
        val result = moviesService.listMovies()

        result.enqueue(object: retrofit2.Callback<List<MovieItem>> {
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
        adapter = MovieListAdapter(listMovies, this, ::clickComment)
        vRecyclerMovies.layoutManager = LinearLayoutManager(this)
        vRecyclerMovies.adapter = adapter
    }

    //SIRVE PARA LA BUSQUEDA DE UN PRODUCTO
    fun filtrar(texto: String) {
        var filtrarLista:MutableList<MovieItem> = ArrayList()

        val moviesService = RestEngine.getRestEngine().create(MoviesService::class.java)
        val result = moviesService.movieSeacrch(texto)
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