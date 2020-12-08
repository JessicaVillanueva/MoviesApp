package com.example.moviesapp.users

import com.example.moviesapp.auth.AuthItem
import com.example.moviesapp.comments.CommentItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {


    //@POST("auth/login")
    //fun login(@Body auth: AuthItem?): Call<UserItem?>?

    @POST("auth/login")
    @FormUrlEncoded
    fun login(@Field("email") email: String,
           @Field("password") password: String): Call<UserItem>

//    @POST("comment")
//    @FormUrlEncoded
//    fun saveComment(
//            @Field("comment") comment: String?,
//            @Field("movie_id") movie_id: Int,
//            @Field("user_id") user_id: Int
//    ): Call<CommentItem?>?
}