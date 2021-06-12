package com.example.fogo.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.fogo.R
import com.example.fogo.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setAction()
    }

    private fun setAction() {
        binding.layoutLogOut.setOnClickListener {

            val builder =
                AlertDialog.Builder(requireContext())
            builder.setTitle("Fogo")
            builder.setMessage("Do you want to log out?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { dialog, id ->
                    firebaseAuth = FirebaseAuth.getInstance()
                    firebaseAuth.signOut()
                    requireActivity().finish()
                }
                .setNegativeButton(
                    "No"
                ) { dialog, id -> dialog.cancel() }
            val alert = builder.create()
            alert.show()

        }

    }
    fun getStatus(): String {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE) ?: return "not email"
        return sharedPref.getString("email", "email").toString()
    }


    private fun initView(view: View) {

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}