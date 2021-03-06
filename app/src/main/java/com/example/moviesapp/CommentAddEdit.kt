package com.example.moviesapp

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviesapp.MovieItems.MovieItem
import com.example.moviesapp.MovieItems.MoviesService
import com.example.moviesapp.Rest.RestEngine
import com.example.moviesapp.comments.CommentItem
import kotlinx.android.synthetic.main.activity_comment_add_edit.*
import retrofit2.Callback

class CommentAddEdit : AppCompatActivity() {

    //var comment: CommentItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_add_edit)
        formSetUp()
    }

    fun formSetUp() {
        when(intent.getStringExtra("TYPE")) {
            "ADD" -> formSetUpAdd()
            "EDIT" -> formSetUpEdit()
        }

        btnCommentCancel.setOnClickListener { onBackPressed() }
    }
    fun formSetUpAdd(){
        //AGREGAR COMENTARIO
        btnCommentSave.setOnClickListener {
            // Validaciones
            if(!EditTComentAddEdit.text.toString().isEmpty()){

                println("comentario " + EditTComentAddEdit.text.toString())
                println("id_movie" +  intent.getIntExtra("IDMOVIE", 0))

                val movie_id = intent.getIntExtra("IDMOVIE", 0)

                val comment: String = EditTComentAddEdit.text.toString()

                val intent = Intent().apply {
                    putExtra("OPTION", "add")
                    putExtra("COMMENT", comment)

                    putExtra("IDMOVIE", movie_id)
                    putExtra("TOKEN", intent.getStringExtra("TOKEN"))
                }

                setResult(Activity.RESULT_OK, intent)
                onBackPressed()
            }
            else{
                showDialogAlertSimple()
            }

        }
    }
    fun formSetUpEdit(){

      }

    fun showDialogAlertSimple() {
        AlertDialog.Builder(this)
            .setTitle("Rellena todos los campos")
            .setMessage("Por favor, Ingresa todos los datos")
            .setPositiveButton("Aceptar",
                DialogInterface.OnClickListener { dialog, which ->
                })
            //.setCancelable(false)
            .show()
    }
}