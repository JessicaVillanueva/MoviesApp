package com.example.moviesapp.Fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moviesapp.MainActivity
import com.example.moviesapp.Movies
import com.example.moviesapp.R
import com.example.moviesapp.Rest.RestEngine
import com.example.moviesapp.auth.AuthItem
import com.example.moviesapp.users.UserItem
import com.example.moviesapp.users.UserService
import kotlinx.android.synthetic.main.fragment_sing_in.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var dialog: ProgressDialog? = null

/**
 * A simple [Fragment] subclass.
 * Use the [SingInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingInFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //private var view_fragment: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_sing_in, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = ProgressDialog(context)
        dialog!!.setCancelable(false)

        btnSignIn.setOnClickListener {
            if (validate()) {
                login()
            }
        }
        txtSignUp.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.frameAuthContainer, SingUpFragment()).commit()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SingInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SingInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun validate(): Boolean {
        txtLayoutEmailSignIn.isErrorEnabled = false
        txtLayoutPasswordSignIn.isErrorEnabled = false

        if (txtEmailSignIn.text.toString().isEmpty()) {
            txtLayoutEmailSignIn.isErrorEnabled = true
            txtLayoutEmailSignIn.error = "Email es requerido"
            return false
        }
        if (txtPasswordSignIn.text.toString().length < 3) {
            txtLayoutPasswordSignIn.isErrorEnabled = true
            txtLayoutPasswordSignIn.error = "Contraseña debe ser mayor a 3 caracteres"
            return false
        }
        return true
    }

    fun login(){
        dialog?.setMessage("Logging in")
        dialog?.show()

        val auth = AuthItem(txtEmailSignIn.text.toString(), txtPasswordSignIn.text.toString())


        val userService = RestEngine.getRestEngine().create(UserService::class.java)
        val result = userService.login(txtEmailSignIn.text.toString(), txtPasswordSignIn.text.toString())

        result?.enqueue(object: Callback<UserItem?> {
            override fun onFailure(call: Call<UserItem?>, t: Throwable) {
                Toast.makeText(context, "Falló la conexión, intente más tarde", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<UserItem?>, response: Response<UserItem?>) {
                println(response.body()?.data?.token)
                if (response.isSuccessful) {
                    val userPref = activity!!.applicationContext.getSharedPreferences("user", Context.MODE_PRIVATE)
                    val editor = userPref.edit()
                    editor.putString("token", "Bearer " + response.body()?.data?.token)
                    editor.putBoolean("isLoggedIn", true)
                    println(response.body()?.data?.token)
                    editor.apply()

                    startActivity(Intent(context as MainActivity?, Movies::class.java))
                    (context as MainActivity).finish()
                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(context, response.body()?.msg.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
        dialog?.dismiss()
    }
}