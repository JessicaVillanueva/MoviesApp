package com.example.moviesapp.comments

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ConmmenstService  {
    @GET("comments")
    fun listComments():Call<List<CommentItem>>

    @GET("comments/{commentId}")
    fun commentDetails(@Path("commentId") commentId:String):Call<CommentItem>
}