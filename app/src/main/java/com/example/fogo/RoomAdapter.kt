package com.example.fogo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fogo.data.model.Room
import java.util.*

class RoomAdapter (
    var roomMutableList: MutableList<Room>,
    var callback: RoomAdapter.Callback,
    var type: Int

) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {


    interface Callback {
        fun onItemClick(index: Int, roomInformation: Room)
        fun onFavoriteClick(index: Int, roomInformation: Room)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        view = if (type == 0) {
            layoutInflater.inflate(R.layout.room_item_normal, parent, false)
        } else {
            layoutInflater.inflate(R.layout.room_item_big, parent, false)
        }
        return ViewHolder(view)
    }

    fun updateData(list: MutableList<Room>){
        this.roomMutableList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room: Room = roomMutableList[position]

        holder.roomName.text = room.getRoomName()
        holder.roomPrice.text= room.price
        holder.roomImage.setImageResource(room.getIconResID())
        holder.roomAddress.text = room.formatAddress()

        holder.itemView.setOnClickListener {
            callback.onItemClick(position, room)
        }
        holder.favoriteImage.setOnClickListener {
            holder.favoriteImage.setImageResource(R.drawable.heart_fill)
            Log.e("Click Favorite", "$position ")
            callback.onFavoriteClick(position, room)
        }
    }

    override fun getItemCount(): Int {
        return roomMutableList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var roomName: TextView = view.findViewById(R.id.room_name)
        var roomPrice: TextView = view.findViewById(R.id.room_price)
        var roomAddress: TextView = view.findViewById(R.id.room_address)
        var roomImage: ImageView = view.findViewById(R.id.room_image)
        var favoriteImage: ImageView = view.findViewById(R.id.favorite)


    }
}