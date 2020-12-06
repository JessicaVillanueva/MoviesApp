package com.example.moviesapp.comments

import retrofit2.Call
import retrofit2.http.*


interface CommentService  {
    /*@PUT("comments/{commentId}")
    @FormUrlEncoded
    fun updateComment(
        @Field("commentId") commentId: String)
        @Field("comment") comment: String?,
        @Field("movie_id") movie_id: Int,
        @Field("user_id") user_id: Int
    ): Call<CommentItem?>?*/

    @GET("comments/{movieId}")
    fun listCommentsForMovie(@Path("movieId") movieId: Int):Call<List<CommentItem>>

    //ruta para guardar comentario
    @POST("comments")
    @FormUrlEncoded
    fun saveComment(
        @Field("comment") comment: String?,
        @Field("movie_id") movie_id: Int,
        @Field("user_id") user_id: Int
    ): Call<CommentItem?>?

}