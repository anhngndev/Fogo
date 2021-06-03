package com.example.fogo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fogo.data.model.CategoryRoom
import com.example.fogo.data.model.Room

class RoomCategoryAdapter(
    private var roomMutableList: MutableList<CategoryRoom>,
    var callback: RoomCategoryAdapter.Callback
) : RecyclerView.Adapter<RoomCategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var roomName: TextView = view.findViewById(R.id.category_name)
        var roomImage: ImageView = view.findViewById(R.id.category_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.room_category_item, parent, false)
        return ViewHolder(view)
    }

    fun  updateData(list: MutableList<CategoryRoom>) {
        this.roomMutableList = list
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return roomMutableList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room: CategoryRoom = roomMutableList[position]

        holder.roomName.text = room.getCityName()
        holder.roomImage.setImageResource(room.getIconResID())

        holder.itemView.setOnClickListener {
            callback.onItemClick(position, room)
        }
    }

    interface Callback {
        fun onItemClick(index: Int, roomInformation: CategoryRoom)
    }
}