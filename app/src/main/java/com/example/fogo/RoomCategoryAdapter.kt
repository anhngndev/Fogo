package com.example.fogo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fogo.data.model.CategoryRoom
import com.example.fogo.data.model.Room
import com.example.fogo.databinding.RoomCategoryItemBinding

class RoomCategoryAdapter(
) : RecyclerView.Adapter<RoomCategoryAdapter.ViewHolder>() {

    var roomMutableList: MutableList<CategoryRoom> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var callback: Callback? = null

    class ViewHolder(val binding : RoomCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<RoomCategoryItemBinding>(
            layoutInflater,
            R.layout.room_category_item,
            parent,
            false
        )
        return ViewHolder(binding)
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

        holder.binding.item = room
        holder.binding.position = position
        holder.binding.itemListener = callback
//        holder.itemView.setOnClickListener {
//            callback?.onItemClick(position, room)
//        }
    }

    interface Callback {
        fun onItemClick(index: Int, roomInformation: CategoryRoom)
    }
}