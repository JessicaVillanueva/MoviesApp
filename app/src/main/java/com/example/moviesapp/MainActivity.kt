package com.example.moviesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapp.Fragments.SingInFragment
import com.example.moviesapp.MovieItems.MovieItem
import kotlinx.android.synthetic.main.fragment_sing_in.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //tenemos que llamar al fragment de abajo para el login
        supportFragmentManager.beginTransaction().add(R.id.frameAuthContainer, SingInFragment()).commit()

        //NECESITA LA AUTENTIFICACIÖN PARA ENTRAR
        // var intent = Intent(this, Movies::class.java)
        //startActivity(intent)


    }
}