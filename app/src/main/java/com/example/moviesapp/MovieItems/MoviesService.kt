package com.example.moviesapp.MovieItems

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService  {
    @GET("movies")
    fun listMovies():Call<List<MovieItem>>

    @GET("movies/{movieId}")
    fun movieDetails(@Path("movieId") movieId:Int):Call<MovieItem>

    @GET("movies")
    fun movieSeacrch(@Query("search") search:String):Call<List<MovieItem>>
}