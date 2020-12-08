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
   /* @PUT("comments/{commentId}")
    fun updateComment(
        @Field("commentId") commentId: String)
    @Body requestBody: RequestBody
    ): Call<CommentItem?>*/

    //trae los comentarios por id


    @GET("comments/{movieId}")
    fun listCommentsForMovie(
            @Header("Authorization") authorization:String,
            @Path("movieId") movieId: Int):Call<List<CommentItem>>

    //ruta para guardar comentario
    @POST("comments/")
    fun saveComment(
        @Header("Authorization") authorization:String?,
        @Body comment: Comment?
    ): Call<CommentItem?>


}