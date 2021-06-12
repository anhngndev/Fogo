package com.example.fogo.ui.home

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fogo.R
import com.example.fogo.data.model.Room
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailRoomFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_detail_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setAction()

//        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.navigation)
//        navBar.visibility = View.GONE

        var args = this.arguments
        val detailRoom = args?.getSerializable("detail_room")
        fillData(detailRoom as Room)
        room = detailRoom
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DetailRoomFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    lateinit var room: Room
    //    lateinit var TAG = "TAG"
    lateinit var callToUse: ImageView
    lateinit var share: ImageView
    lateinit var back: ImageView
    lateinit var roomImage: ImageView
    lateinit var userNumber: TextView
    lateinit var roomName: TextView
    lateinit var roomAddress: TextView
    lateinit var roomPrice: TextView
    lateinit var roomAcreage: TextView

    private fun setAction() {
        callToUse.setOnClickListener {
            val phone: String = room.phone
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }

        share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        roomPrice.setOnClickListener {
            val dateSetListener =
                OnDateSetListener { view, year, monthOfYear, dayOfMonth -> roomPrice.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year) }
            // Create DatePickerDialog (Spinner Mode):
            val datePickerDialog = activity?.let { it1 ->
                DatePickerDialog(
                    it1,
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    dateSetListener, 2021, 5, 1
                )
            }
            // Show
            datePickerDialog?.show()
        }

    }

    private fun initView(view: View) {
        roomImage = view.findViewById(R.id.room_image)
        share = view.findViewById(R.id.share)
        callToUse = view.findViewById(R.id.call_to_user)
        back = view.findViewById(R.id.back)
        userNumber = view.findViewById(R.id.user_number)
        roomPrice = view.findViewById(R.id.price)
        roomAcreage = view.findViewById(R.id.room_acreage)
        roomName = view.findViewById(R.id.room_name)
        roomAddress = view.findViewById(R.id.room_address)


    }

    private fun fillData(room: Room) {

        roomImage.setImageResource(room.getIconResID())
        userNumber.text = room.phone
        roomName.text = room.getRoomName()
        roomAddress.text = room.formatAddress()
        roomPrice.text = "$ " + room.price
        roomAcreage.text = room.acreage
    }


}