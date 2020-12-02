package com.example.moviesapp.MovieItems

import com.example.moviesapp.comments.CommentItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesService  {
    @GET("movies")
    fun listMovies():Call<List<MovieItem>>

    @GET("movies/{movieId}")
    fun movieDetails(@Path("movieId") movieId:String):Call<MovieItem>
}