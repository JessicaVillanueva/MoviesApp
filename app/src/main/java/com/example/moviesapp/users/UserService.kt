package com.example.moviesapp.users

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
}