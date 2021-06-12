package com.example.fogo.ui.login

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.fogo.R
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {


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
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setAction()
    }

    lateinit var mail: EditText
    lateinit var pass: EditText
    lateinit var passAgain: EditText
    lateinit var signUp: TextView
    lateinit var reSend: LinearLayout
    lateinit var back: ImageView

    private fun setAction() {

        back.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        signUp.setOnClickListener {

            val firebaseAuth = FirebaseAuth.getInstance()
            val mail = mail.text
            val pass = pass.text
            val passAgain = passAgain.text

            if (mail.toString().equals("")) {
                this.mail.error = "Type"
            }
            if (this.pass.text.toString().equals("")) {
                this.pass.error = "Type"
            }
            if (!pass.toString().equals(passAgain.toString())) {
                this.passAgain.error = "Incorrect password"
            }

            if (checkInput()) {
                val mail = this.mail.text.toString()
                val pass = this.pass.text.toString()
                firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Sign up success!", Toast.LENGTH_SHORT).show()
                        Handler().postDelayed(
                            Runnable {
                                activity?.supportFragmentManager?.popBackStack()
                            }, 1000
                        )

                    }
                }.addOnFailureListener {
                    Toast.makeText(context, "Sign up failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initView(view: View) {
        signUp = view.findViewById(R.id.button_sign_up)
        mail = view.findViewById(R.id.mail)
        pass = view.findViewById(R.id.password)
        passAgain = view.findViewById(R.id.pass_again)
        reSend = view.findViewById(R.id.resend)
        back = view.findViewById(R.id.back)
    }

    private fun checkInput(): Boolean {
        if (mail.text.toString().equals("") || pass.text.toString()
                .equals("") || !pass.text.toString().equals(passAgain.text.toString())
        ) return false
        return true
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SignUpFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}