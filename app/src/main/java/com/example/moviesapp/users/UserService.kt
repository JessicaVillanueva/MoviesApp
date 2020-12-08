package com.example.moviesapp.users

import com.example.moviesapp.auth.AuthItem
import com.example.moviesapp.comments.Comment
import com.example.moviesapp.comments.CommentItem
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST("auth/login")
    @FormUrlEncoded
    fun login(@Field("email") email: String,
           @Field("password") password: String): Call<UserItem>



    //ruta para crear un usuario
    @POST("users")
    fun saveUser(
        @Body user: User?
    ): Call<User?>

//    @POST("comment")
//    @FormUrlEncoded
//    fun saveComment(
//            @Field("comment") comment: String?,
//            @Field("movie_id") movie_id: Int,
//            @Field("user_id") user_id: Int
//    ): Call<CommentItem?>?
}