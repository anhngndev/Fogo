package com.example.fogo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fogo.data.model.MenusModel

class MenusAdapterTest (
    var baseList: MutableList<MenusModel>,
    var tools:String,
    var toolList: MutableList<MenusModel>,
    var markets:String,
    var marketList: MutableList<MenusModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.TYPE_1.type -> {
                val view: View =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.menu_item, parent, false)
                Holder1(view)
            }
            ViewType.TYPE_2.type -> {
                val view: View =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.menu_item_2, parent, false)
                Holder2(view)
            }
            else -> {
                val view: View =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.menu_item_3, parent, false)
                Holder3(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return baseList.size + toolList.size + marketList.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            in 0 until baseList.size -> ViewType.TYPE_1.type
            baseList.size -> ViewType.TYPE_3.type
            in baseList.size + 1 until baseList.size + toolList.size-> ViewType.TYPE_2.type
            baseList.size + toolList.size +1 -> ViewType.TYPE_3.type
            else -> ViewType.TYPE_2.type
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (position) {
            in 0 until baseList.size ->{
                var menu = baseList[position]
                holder.apply {
                    if (holder is Holder1) holder.bind(menu)
                }
            }
            baseList.size -> {
                holder.apply {
                    if (holder is Holder3) holder.bind(tools)
                }
            }
            in baseList.size + 1 until baseList.size + toolList.size +1->{
                var menu = toolList[position-baseList.size - 1]
                holder.apply {
                    if (holder is Holder2) holder.bind(menu)
                }
            }
            baseList.size + toolList.size +1 -> {
                if (holder is Holder3) holder.bind(markets)
            }
            in baseList.size + toolList.size + 2 until baseList.size + toolList.size + marketList.size + 2-> {
                var menu = marketList[position-baseList.size - toolList.size -2]
                holder.apply {
                    if (holder is Holder2) holder.bind(menu)
                }
            }
        }
    }

    class Holder3(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        fun bind(item: String) {
            with(itemView) {
                title.text = item
            }
        }
    }

    class Holder2(view: View) : RecyclerView.ViewHolder(view) {
        var layout: ConstraintLayout = view.findViewById(R.id.layout)
        var title: TextView = view.findViewById(R.id.title)
        var image: ImageView = view.findViewById(R.id.image)
        fun bind(item: MenusModel) {
            with(itemView) {
                title.text = item.title
                image.setImageResource(item.resourceId)
            }
        }
    }

    class Holder1(view: View) : RecyclerView.ViewHolder(view) {
        var layout: ConstraintLayout = view.findViewById(R.id.layout)
        var title: TextView = view.findViewById(R.id.title)
        var image: ImageView = view.findViewById(R.id.image)
        fun bind(item: MenusModel) {
            with(itemView) {
                title.text = item.title
                image.setImageResource(item.resourceId)
            }
        }
    }

    interface CallBack {
        fun onClickMenuItem(int: Int, messModel: MenusModel)
    }

    enum class ViewType(val type: Int) {
        TYPE_1(0),
        TYPE_2(1),
        TYPE_3(2)
    }
}