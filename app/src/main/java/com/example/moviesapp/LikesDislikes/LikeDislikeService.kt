package com.example.moviesapp.LikesDislikes


import retrofit2.Call
import retrofit2.http.*

interface LikeDislikeService {
    @GET("likes/{movieId}")
    fun listLikesForMovie(
            @Header("Authorization") authorization:String,
            @Path("movieId") movieId: Int): Call<List<LikesDisItem>>

    @POST("likes/")
    fun saveLike(
            @Header("Authorization") authorization:String?,
            @Body criterio: LikeDislike?
    ): Call<LikesDisItem?>

    @GET("dislikes/{movieId}")
    fun listDisikesForMovie(
            @Header("Authorization") authorization:String,
            @Path("movieId") movieId: Int): Call<List<LikesDisItem>>

    @POST("dislikes/")
    fun saveDislike(
            @Header("Authorization") authorization:String?,
            @Body criterio: LikeDislike?
    ): Call<LikesDisItem?>
}