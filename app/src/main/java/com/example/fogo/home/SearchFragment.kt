package com.example.fogo.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.fogo.R
import com.example.fogo.RoomAdapter
import com.example.fogo.data.model.CategoryRoom
import com.example.fogo.data.model.Room
import com.example.fogo.data.source.remote.HttpRequestMethod
import com.example.fogo.data.source.remote.HttpRequestTask
import com.example.fogo.utils.NetWorkUtils
import com.google.android.material.bottomnavigation.BottomNavigationView


class SearchFragment : Fragment(), RoomAdapter.Callback, HttpRequestTask.Callback {

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        Log.d(TAG, "onCreate: " + "x")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: " + "x")
        initView(view)

        var args = this.arguments
        try {
            val temp: CategoryRoom = args?.getSerializable("detail room") as CategoryRoom
            room = temp
        } catch (e: Exception) {
        }
        cityID = room.id

        when (cityID) {
            "1581130" -> {
                spinner.setSelection(0)
            }
            "6058560" -> {
                spinner.setSelection(1)
            }
            "1580142" -> {
                spinner.setSelection(2)
            }
            "1580410" -> {
                spinner.setSelection(3)
            }
            "1566346" -> {
                spinner.setSelection(4)
            }


        }
//        handler.post(getData)
//        getRoomData(cityID)



        setAction()

    }


//    override fun onResume() {
//        super.onResume()
//        getRoomData(cityID)
//    }

    var room = CategoryRoom("1581130")

    //    val navBar: BottomNavigationView = requireActivity().findViewById(R.id.navigation)
    private val DO_MAIN: String = "http://api.openweathermap.org/data/2.5/forecast/daily?id="
    private val PARAM: String = "&appid=b1a6b9d8867fa058c1a2f803e6244b14"
    var cityID: String = "1581130"

    lateinit var roomAdapterSuggest: RoomAdapter
    lateinit var recyclerViewSuggest: RecyclerView
    lateinit var listRoom: MutableList<Room>
    lateinit var listRoomCurrent: MutableList<Room>
    lateinit var progressBar: ProgressBar
    lateinit var search: EditText
    lateinit var book: TextView
    lateinit var sumResult: TextView
    lateinit var spinner: Spinner

    var handler = Handler()
    var hideNavigation = Runnable {
        val navBar: BottomNavigationView? = activity?.findViewById(R.id.navigation)
        navBar?.visibility = View.GONE
        val shadowBottom: View? = activity?.findViewById(R.id.shadow_bottom)
        shadowBottom?.visibility = View.GONE
    }

    var getData = Runnable {
        getRoomData(cityID)
    }

    private fun initView(view: View) {
        spinner = view.findViewById(R.id.city_spinner)
        sumResult = view.findViewById(R.id.sum_result)
        recyclerViewSuggest = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress)
        search = view.findViewById(R.id.search_edit_text)

        listRoom = mutableListOf()
        listRoomCurrent = mutableListOf()

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        recyclerViewSuggest.layoutManager = staggeredGridLayoutManager
        roomAdapterSuggest = RoomAdapter(listRoom, this, 1)
        recyclerViewSuggest.adapter = roomAdapterSuggest

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.city_name,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply  adapter to the spinner
            spinner.adapter = adapter

        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> getRoomData("1581130")
                    1 -> getRoomData("6058560")
                    2 -> getRoomData("1580142")
                    3 -> getRoomData("1566346")
                    4 -> getRoomData("1580410")
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }


//        navBar.selectedItemId = R.id.search
    }

    private fun getRoomData(cityID: String) {
//        val baseUrl =
//            "http://api.openweathermap.org/data/2.5/forecast/daily?id=1581130&appid=b1a6b9d8867fa058c1a2f803e6244b14"
        val baseUrl = DO_MAIN + cityID + PARAM
        Log.d(TAG, "getRoomData: $cityID")
        val task = HttpRequestTask(HttpRequestMethod.GET, this)

        task.execute(baseUrl)
    }

    private fun setAction() {
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                findWithKey(search.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

    }

    private fun findWithKey(key: String) {
        listRoomCurrent = listRoom
        val roomsFiltered: MutableList<Room>? = mutableListOf()
        for (d in listRoomCurrent) {
            if (d.getRoomName().toLowerCase().contains(key)) {
                roomsFiltered?.add(d)
            }
        }
        sumResult.text = roomsFiltered?.size.toString()
        recyclerViewSuggest.adapter = roomsFiltered?.let { RoomAdapter(it, this, 1) }
        Log.e("Size:", roomsFiltered?.size.toString() + "")
    }


    override fun onSuccess(result: String?) {
        val arrayList = NetWorkUtils.jsonToRoomList(result)
        listRoom = arrayList
        roomAdapterSuggest.updateData(arrayList)
        progressBar.visibility = View.GONE
        Log.e("TAG", result!!)
    }

    override fun onFailed(error: Exception?) {
        progressBar.visibility = View.GONE
        error?.printStackTrace()
        Log.e("TAG", "not connection")

    }

    override fun onItemClick(index: Int, roomInformation: Room) {
        val detailRoomFragment = DetailRoomFragment.newInstance()
        val bundle = Bundle()

        bundle.putSerializable("detail room", roomInformation)
        bundle.putString("name", roomInformation.getRoomName())
        detailRoomFragment.arguments = bundle

        handler.postDelayed(hideNavigation, 100)

        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(
                R.animator.slide_up,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.slide_down
            )
            ?.add(R.id.container, detailRoomFragment, "detail room")
            ?.addToBackStack("detail room")?.commit()
    }

    override fun onFavoriteClick(index: Int, roomInformation: Room) {
        TODO("Not yet implemented")
    }
}