package com.example.moviesapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesapp.R
import kotlinx.android.synthetic.main.fragment_sing_in.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SingInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingInFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var view_fragment: View? = null

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
        view_fragment = inflater.inflate(R.layout.fragment_sing_in, container, false)
        init()
        return view_fragment
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

    private fun init(){
        //abre el fragment para crear un usuario
        txtSignUp.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frameAuthContainer, SingUpFragment()).commit()
        }

        btnSignIn.setOnClickListener {
            if (validate()) {
               // login()
            }
        }
    }

    private fun validate(): Boolean {
        if (txtEmailSignIn.getText().toString().isEmpty()) {
            txtLayoutEmailSignIn.setErrorEnabled(true)
            txtLayoutEmailSignIn.setError("Email es requerido")
            return false
        }
        if (txtPasswordSignIn.getText().toString().length < 8) {
            txtLayoutPasswordSignIn.setErrorEnabled(true)
            txtLayoutPasswordSignIn.setError("Contraseña debe ser mayor a 8 caracteres")
            return false
        }
        return true
    }
}