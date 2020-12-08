package com.example.moviesapp.MovieItems

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService  {
    @GET("movies/")
    fun listMovies(@Header("Authorization") authorization:String):Call<List<MovieItem>>

    @GET("movies/{movieId}")
    fun movieDetails(@Header("Authorization") authorization:String, @Path("movieId") movieId:Int):Call<MovieItem>

    @GET("movies/")
    fun movieSeacrch(@Header("Authorization") authorization:String, @Query("search") search:String):Call<List<MovieItem>>
}