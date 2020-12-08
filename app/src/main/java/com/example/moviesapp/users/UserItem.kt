package com.example.moviesapp.users

import com.example.moviesapp.auth.Token

data class UserItem(var status:Int, var msg:String, var data: Token)