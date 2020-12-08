package com.example.moviesapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapp.Fragments.SingInFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userPref = applicationContext.getSharedPreferences("user", Context.MODE_PRIVATE)
        val isLoggedIn = userPref.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            //si el token esta activo
            startActivity(Intent(this@MainActivity, Movies::class.java))
            finish()
        } else {
            //si el token no esta activo
            supportFragmentManager.beginTransaction().add(R.id.frameAuthContainer, SingInFragment()).commit()
        }



        //NECESITA LA AUTENTIFICACIÖN PARA ENTRAR
        //var intent = Intent(this, Movies::class.java)
        //startActivity(intent)


    }
}