package com.example.moviesapp.comments

data class CommentItem (
    val id:Int,
    val comment:String,
    val date_comment:String,
    val name_user:String,
    val movie_id:Int
)