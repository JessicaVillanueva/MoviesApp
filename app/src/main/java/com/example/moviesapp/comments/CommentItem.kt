package com.example.moviesapp.comments

data class CommentItem (
    val id:Int,
    val comment:String,
    val date:String,
    val movie_id:Int,
    val user_id:Int,
    val username:String

)