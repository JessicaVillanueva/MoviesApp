package com.example.moviesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapp.Fragments.SingInFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.frameAuthContainer, SingInFragment()).commit()

    }
}