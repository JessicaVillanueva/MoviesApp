package com.example.moviesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapp.Fragments.SingInFragment
import com.example.moviesapp.MovieItems.MovieItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //tenemos que llamar al fragment de abajo para el login
        //supportFragmentManager.beginTransaction().add(R.id.frameAuthContainer, SingInFragment()).commit()
        var intent = Intent(this, Movies::class.java)
        startActivity(intent)


    }
}