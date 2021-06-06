package com.example.fogo.home

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.fogo.R
import com.example.fogo.RoomAdapter
import com.example.fogo.RoomCategoryAdapter
import com.example.fogo.data.model.CategoryRoom
import com.example.fogo.data.model.Room
import com.example.fogo.data.source.remote.HttpRequestMethod
import com.example.fogo.data.source.remote.HttpRequestTask
import com.example.fogo.utils.NetWorkUtils
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment(), RoomAdapter.Callback, HttpRequestTask.Callback,
    RoomCategoryAdapter.Callback {


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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)

        handler.post(getData)

        setAction()
    }

    lateinit var recyclerViewSuggest: RecyclerView
    lateinit var recyclerViewCategory: RecyclerView
    lateinit var roomAdapterSuggest: RoomAdapter
    lateinit var roomCategoryAdapter: RoomCategoryAdapter
    lateinit var listRoom: MutableList<Room>
    lateinit var listCategoryRoom: MutableList<CategoryRoom>

    lateinit var currentLocation: TextView
    lateinit var search: EditText
    lateinit var progressBar: ProgressBar
    var handler = Handler()
    var hideNavigation = Runnable {
        val navBar: BottomNavigationView? = activity?.findViewById(R.id.navigation)
        navBar?.visibility = View.GONE
        val shadowBottom: View? = activity?.findViewById(R.id.shadow_bottom)
        shadowBottom?.visibility = View.GONE
    }

    var getData = Runnable {
        getRoomData()
    }

    val enter = R.animator.slide_in
    private val exit = R.animator.fade_out
    private val popEnter = R.animator.fade_in
    val popExit = R.animator.slide_out

//    lateinit var roomCategoryAdapter: RoomCategoryAdapter

    private fun initView(view: View) {
        recyclerViewCategory = view.findViewById(R.id.room_category_recycler_view)
        recyclerViewSuggest = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress)
        currentLocation = view.findViewById(R.id.current_location)
        search = view.findViewById(R.id.search_edit_text)
        listRoom = mutableListOf()
        listCategoryRoom = mutableListOf()

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL)
        val staggeredGridLayoutManager1 = StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL)

        recyclerViewSuggest.layoutManager = staggeredGridLayoutManager
        recyclerViewCategory.layoutManager = staggeredGridLayoutManager1

        roomAdapterSuggest = RoomAdapter(listRoom, this, 0)
        roomCategoryAdapter = RoomCategoryAdapter(listCategoryRoom, this)

        recyclerViewSuggest.adapter = roomAdapterSuggest
        recyclerViewCategory.adapter = roomCategoryAdapter

    }

    private fun setAction() {
//        search.setOnClickListener {
//
//            val navBar: BottomNavigationView? = activity?.findViewById(R.id.navigation)
////            navBar?.visibility = View.VISIBLE
//            navBar?.selectedItemId = R.id.search
//
//
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.container, SearchFragment.newInstance(), "search")
//                ?.addToBackStack("search")?.commit()
//
//        }

//        search.setOnFocusChangeListener { view, b ->
//            search.isEnabled = false
//            val navBar: BottomNavigationView? = activity?.findViewById(R.id.navigation)
//            navBar?.selectedItemId = R.id.search
//
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.container, SearchFragment.newInstance(), "search")
//                ?.addToBackStack("search")?.commit()
//        }

    }

    private fun getRoomData() {
        val baseUrl =
            "http://api.openweathermap.org/data/2.5/forecast/daily?id=1581130&appid=b1a6b9d8867fa058c1a2f803e6244b14"
        Log.e("TAG", "connect")
        val task = HttpRequestTask(HttpRequestMethod.GET, this)

        task.execute(baseUrl)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onItemClick(index: Int, roomInformation: Room) {

        val detailRoomFragment = DetailRoomFragment.newInstance()
        val bundle = Bundle()

        bundle.putSerializable("detail room", roomInformation)
        bundle.putString("name", roomInformation.getRoomName())
        detailRoomFragment.arguments = bundle

        handler.postDelayed(hideNavigation, 100)

        activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
            R.animator.slide_up,
            exit,
            popEnter,
            R.animator.slide_down
        )
            ?.replace(R.id.container, detailRoomFragment, "detail room")
            ?.addToBackStack("detail room")?.commit()
    }

    override fun onFavoriteClick(index: Int, roomInformation: Room) {
    }

    override fun onSuccess(result: String?) {
        val arrayList = NetWorkUtils.jsonToRoomList(result)
        roomAdapterSuggest.updateData(arrayList)

        val categoryList = NetWorkUtils.jsonToRoomCategory(result)
        roomCategoryAdapter.updateData(categoryList)

        val cityName = NetWorkUtils.getCityFromJson(result)
        currentLocation.text = cityName

        progressBar.visibility = View.GONE
        Log.e("TAG", result!!)
    }

    override fun onFailed(error: Exception?) {
        Log.e("TAG", "not connection")
        progressBar.visibility = View.GONE
        error?.printStackTrace()
    }

    override fun onItemClick(index: Int, roomInformation: CategoryRoom) {
//

        val searchFragment = SearchFragment.newInstance()
        val bundle2 = Bundle()

        bundle2.putSerializable("detail room", roomInformation)
//
        searchFragment.arguments = bundle2
        handler.postDelayed(hideNavigation, 100)

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, searchFragment)
            .addToBackStack("search").commit()
    }
}