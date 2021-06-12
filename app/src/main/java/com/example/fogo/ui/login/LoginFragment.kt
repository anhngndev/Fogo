package com.example.fogo.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.fogo.R
import com.example.fogo.ui.home.FacebookLoginActivity
import com.example.fogo.ui.home.HomeActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener


class LoginFragment : Fragment() {

    val TAG = "LoginFragment"

    lateinit var mail: EditText
    lateinit var fb: ImageView
    lateinit var pass: EditText
    lateinit var forgotPass: TextView
    lateinit var login: TextView
    lateinit var signUp: LinearLayout
    lateinit var firebaseAuth: FirebaseAuth
//    lateinit var callbackManager: CallbackManager
//    lateinit var authStateListener: AuthStateListener
//    lateinit var accessTokenTracker: AccessTokenTracker

    var handler = Handler()
    var runnable = Runnable {
        login.isEnabled = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setAction()
    }

//    override fun onStart() {
//        super.onStart()
//        firebaseAuth.addAuthStateListener(authStateListener)
//
//    }
//
//    override fun onStop() {
//        super.onStop()
//        firebaseAuth.addAuthStateListener(authStateListener)
//
//    }

    private fun initView(view: View) {
        fb = view.findViewById(R.id.login_with_fb)

        mail = view.findViewById(R.id.mail)
        pass = view.findViewById(R.id.password)
        login = view.findViewById(R.id.button_login)
        signUp = view.findViewById(R.id.sign_up)
        forgotPass = view.findViewById(R.id.forgot_password)

        firebaseAuth = FirebaseAuth.getInstance()
//        FacebookSdk.sdkInitialize(context)

//        callbackManager = CallbackManager.Factory.create()

    }

    private fun setAction() {
        fb.setOnClickListener {
            startActivity(Intent(requireActivity(),FacebookLoginActivity::class.java))
        }

//        accessTokenTracker = object : AccessTokenTracker() {
//            //check token
//            override fun onCurrentAccessTokenChanged(
//                oldAccessToken: AccessToken,
//                currentAccessToken: AccessToken
//            ) {
//                if (currentAccessToken != null) {
//                    //bình thường đoạn này là chuyển activity mới, nghĩa là đăng nhập trước đấy rồi thì k cần đăng nhập lại mà qua actiivyt mới luôn nhưng mà t muốn mỗi lần vào đều đăng nhập lại nên t getLogout OK
////                    val intent = Intent(requireActivity(), HomeActivity::class.java)
////                    startActivity(intent)
//
//                    handleFacebookToken(currentAccessToken)
//                }
//            }
//        }
//
//        authStateListener = AuthStateListener { firebaseAuth ->
//            val user = firebaseAuth.currentUser
//            if (user != null) {
//                LoginManager.getInstance().logOut()
//            }
//        }
//
//        fb.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
//            // show login window
//            override fun onSuccess(loginResult: LoginResult) {
//                Log.e(TAG, "onSuccess: $loginResult")
//                handleFacebookToken(loginResult.accessToken)
//            }
//
//            override fun onCancel() {
//                Log.e(TAG, "onCancel: login")
//            }
//
//            override fun onError(e: FacebookException) {
//                Log.e(TAG, "onError: login")
//            }
//        })

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

//    private fun handleFacebookToken(accessToken: AccessToken) { //cái này là xử lý đẩy tài khoản fb lên firebase, bình thường m đăng nhập fb k đẩy lên fb vẫn được, nhưng t đẩy lên firebase để dùng như email bình thường, hiểu ý k :vOK
//        Log.d("FACEBOOK_RESULT", "handle login user")
//        val authCredential = FacebookAuthProvider.getCredential(accessToken.token)
//        firebaseAuth.signInWithCredential(authCredential)
//            .addOnCompleteListener(requireActivity(),
//                OnCompleteListener<AuthResult?> { task ->
//                    if (task.isSuccessful) {
//                        Log.d("FACEBOOK_RESULT", "sign in with credential successful")
//                        val intent = Intent(requireActivity(), HomeActivity::class.java)
//                        startActivity(intent)
//                    } else {
//                        Log.d("FACEBOOK_RESULT", "sign in with credential failure", task.exception)
//                    }
//                })
//    }

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