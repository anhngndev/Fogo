package com.example.fogo.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.fogo.MenusAdapterTest
import com.example.fogo.R
import com.example.fogo.data.model.MenusModel
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
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setAction()
    }

    private lateinit var recyclerViewBase: RecyclerView
    private lateinit var mutableListBase: MutableList<MenusModel>
    private lateinit var mutableListTools: MutableList<MenusModel>
    private lateinit var mutableListMarkets: MutableList<MenusModel>
    private lateinit var menusAdapterTest: MenusAdapterTest
    private lateinit var edittextEmail: EditText

    private fun setAction() {
        binding.tvLogout.setOnClickListener {

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
        var staggeredGridLayoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        recyclerViewBase = view.findViewById(R.id.recycler_view_1)
        edittextEmail = view.findViewById(R.id.search_edit_text)
        edittextEmail.hint = getStatus()

        mutableListBase = mutableListOf()
        mutableListBase.add(MenusModel(R.drawable.icons_8_alarm, "Alerts"))
        mutableListBase.add(MenusModel(R.drawable.icons_8_left_and_right_arrows, "Predictions"))
        mutableListBase.add(MenusModel(R.drawable.icons_8_pin, "Saved elements"))
        mutableListBase.add(MenusModel(R.drawable.icons_8_no_entry, "Remove Ads"))

        mutableListTools = mutableListOf()
        mutableListTools.add(MenusModel(R.drawable.icons_8_profit_2, "Select Stocks"))
        mutableListTools.add(MenusModel(R.drawable.icons_8_swap, "Currency Exchange"))
        mutableListTools.add(MenusModel(R.drawable.icons_8_video_call, "Webinar"))
        mutableListTools.add(MenusModel(R.drawable.icons_8_rent, "Best Broker"))

        mutableListMarkets = mutableListOf()
        mutableListMarkets.add(MenusModel(R.drawable.icons_8_profit_2, "Select Stocks"))

        menusAdapterTest = MenusAdapterTest(
            mutableListBase,
            "Tools",
            mutableListTools,
            "Market",
            mutableListMarkets
        )
        recyclerViewBase.layoutManager = staggeredGridLayoutManager
        recyclerViewBase.adapter = menusAdapterTest
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