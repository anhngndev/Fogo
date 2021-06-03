package com.example.fogo.home

import android.os.Bundle
import android.os.Handler
import android.os.Message
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
import com.example.fogo.data.model.Room
import com.example.fogo.data.source.remote.HttpRequestMethod
import com.example.fogo.data.source.remote.HttpRequestTask
import com.example.fogo.utils.HandlerManager.Companion.handler
import com.example.fogo.utils.NetWorkUtils
import com.google.android.material.bottomnavigation.BottomNavigationView


class SearchFragment : Fragment(), RoomAdapter.Callback, HttpRequestTask.Callback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        handler.post(getData)
        setAction()
    }

    lateinit var roomAdapterSuggest: RoomAdapter
    lateinit var recyclerViewSuggest: RecyclerView
    lateinit var listRoom: MutableList<Room>
    lateinit var progressBar: ProgressBar
    lateinit var search: EditText
    lateinit var book: TextView
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

    private fun initView(view: View) {
        recyclerViewSuggest = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress)
        search = view.findViewById(R.id.search_edit_text)
        listRoom = mutableListOf()

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        recyclerViewSuggest.layoutManager = staggeredGridLayoutManager
        roomAdapterSuggest = RoomAdapter(listRoom, this, 1)
        recyclerViewSuggest.adapter = roomAdapterSuggest


    }

    private fun getRoomData() {
        val baseUrl =
            "http://api.openweathermap.org/data/2.5/forecast/daily?id=1581130&appid=b1a6b9d8867fa058c1a2f803e6244b14"
        Log.e("TAG", "connect")
        val task = HttpRequestTask(HttpRequestMethod.GET, this)

        task.execute(baseUrl)
    }

    private fun setAction() {


    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
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
            ?.replace(R.id.container, detailRoomFragment, "detail room")
            ?.addToBackStack("detail room")?.commit()
    }

    override fun onFavoriteClick(index: Int, roomInformation: Room) {
        TODO("Not yet implemented")
    }
}