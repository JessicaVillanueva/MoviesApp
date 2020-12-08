package com.example.moviesapp.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesapp.R
import com.example.moviesapp.Rest.RestEngine
import com.example.moviesapp.users.User
import com.example.moviesapp.users.UserItem
import com.example.moviesapp.users.UserService
import kotlinx.android.synthetic.main.fragment_sing_in.*
import kotlinx.android.synthetic.main.fragment_sing_up.*
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
 * Use the [SingUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingUpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view: View = inflater.inflate(R.layout.fragment_sing_up, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = ProgressDialog(context)
        dialog!!.setCancelable(false)

        txtSignIn.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frameAuthContainer, SingInFragment()).commit()
        }
        btnSignUp.setOnClickListener {
            if(validate()) {
                register()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SingUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SingUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun validate(): Boolean {
        txtLayoutEmailSignUp.isErrorEnabled = false
        txtLayoutPasswordSignUp.isErrorEnabled = (false)
        txtLayoutConfirmPasswordSignUp.isErrorEnabled = (false)
        txtLayoutNameSignUp.isErrorEnabled = (false)
        txtLayoutLnPaternalSignUp.isErrorEnabled = (false)
        txtLayoutLnMaternalSignUp.isErrorEnabled = (false)

        if (txtEmailSignUp.text.toString().isEmpty()) {
            txtLayoutEmailSignUp.isErrorEnabled = (true)
            txtLayoutEmailSignUp.error = "Email es requerido"
            return false
        }
        if (txtPasswordSignUp.text.toString().length < 3) {
            txtLayoutPasswordSignUp.isErrorEnabled = (true)
            txtLayoutPasswordSignUp.error = "Contraseña debe ser mayor a 3 caracteres"
            return false
        }
        if (!txtConfirmPasswordSignUp.text.toString().equals(txtPasswordSignUp.getText().toString())) {
            txtLayoutConfirmPasswordSignUp.isErrorEnabled = true
            txtLayoutConfirmPasswordSignUp.error = "Las contraseñas no coinciden"
            return false
        }
        if (txtNameSignUp.text.toString().isEmpty()) {
            txtLayoutNameSignUp.isErrorEnabled = (true)
            txtLayoutNameSignUp.error = "Name es requerido"
            return false
        }
        if (txtLnPaternalSignUp.text.toString().isEmpty()) {
            txtLayoutLnPaternalSignUp.isErrorEnabled = (true)
            txtLayoutLnPaternalSignUp.error = "LnPaternal es requerido"
            return false
        }
        if (txtLnMaternalSignUp.text.toString().isEmpty()) {
            txtLayoutLnMaternalSignUp.isErrorEnabled = (true)
            txtLayoutLnMaternalSignUp.error = "LnMaternal es requerido"
            return false
        }
        return true
    }

    private fun register(){
        dialog!!.setMessage("Registering")
        dialog!!.show()

        val email: String =  txtEmailSignUp.text.toString()
        val password: String =  txtPasswordSignUp.text.toString()
        val name: String =  txtNameSignUp.text.toString()
        val lnPaternal: String =  txtLnPaternalSignUp.text.toString()
        val lnMaternal: String =  txtLnMaternalSignUp.text.toString()

        val user = User(email, password, name, lnPaternal, lnMaternal)

        val userService = RestEngine.getRestEngine().create(UserService::class.java)
        val result = userService.saveUser(user)

        result.enqueue(object: Callback<User?> {
            override fun onFailure(call: Call<User?>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (response.isSuccessful) {
                    activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.frameAuthContainer, SingInFragment()).commit()
                }
            }
        })
        dialog?.dismiss()
    }
}