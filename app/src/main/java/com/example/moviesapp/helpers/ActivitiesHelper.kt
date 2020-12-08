package com.example.moviesapp.helpers

import android.content.Context
import android.content.Intent
import com.example.moviesapp.CommentAddEdit
import com.example.moviesapp.comments.CommentItem

class ActivitiesHelper {
    val OPEN_ADD_TODO_RID = 0
    val OPEN_EDIT_TODO_RID = 1
    fun openAddTodo(ctx: Context, movie_id: Int, token: String): Intent {
        val intent = Intent(ctx, CommentAddEdit::class.java).apply {
            putExtra("TYPE", "ADD")
            putExtra("IDMOVIE", movie_id)
            putExtra("TOKEN", token)
        }
        return intent
    }
    fun openEditTodo(ctx: Context, itemData: CommentItem): Intent {
        //No necesito todos lo valores
        val intent = Intent(ctx, CommentAddEdit::class.java).apply {
            putExtra("TYPE", "EDIT")
            putExtra("ID", itemData.id)
            putExtra("COMMENT", itemData.comment)
            putExtra("DATE", itemData.date)
            putExtra("MOVIE_ID", itemData.movie_id)
            putExtra("USER_ID", itemData.user_id)
            putExtra("USERNAME", itemData.username)
        }
        return intent
    }
}
