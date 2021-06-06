package com.example.fogo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.fogo.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setAction()
    }

    lateinit var mail: EditText
    lateinit var pass: EditText
    lateinit var forgotPass: TextView
    lateinit var login: TextView
    lateinit var signUp: LinearLayout

    lateinit var firebaseAuth: FirebaseAuth

    var handler = Handler()
    var runnable = Runnable {
        login.isEnabled = true
    }

    private fun initView(view: View) {
        mail = view.findViewById(R.id.mail)
        pass = view.findViewById(R.id.password)
        login = view.findViewById(R.id.button_login)
        signUp = view.findViewById(R.id.sign_up)
        forgotPass = view.findViewById(R.id.forgot_password)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun setAction() {
        signUp.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, SignUpFragment.newInstance())?.addToBackStack("sign up")
                ?.commit()
        }

        login.setOnClickListener {
            val mail = mail.text
            val pass = pass.text

            login.isEnabled = false
            handler.postDelayed(runnable, 1700)

            if (checkInput()) {
                firebaseAuth.signInWithEmailAndPassword(mail.toString(), pass.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(requireActivity(), HomeActivity::class.java)
                            saveEmail("email")
                            startActivity(intent)
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Login failed!", Toast.LENGTH_SHORT).show()
                    }

            } else {
                if (mail.toString().equals("")) {
                    this.mail.error = "Type"
                }
                if (pass.toString().equals("")) {
                    this.pass.error = "Type"
                }
                Toast.makeText(context, "Please type full!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkInput(): Boolean {
        if (mail.text.toString().equals("") || pass.text.toString()
                .equals("")
        ) return false
        return true
    }



    companion object {

        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    fun saveEmail(email: String) {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("email", email)
            apply()
        }
    }
}