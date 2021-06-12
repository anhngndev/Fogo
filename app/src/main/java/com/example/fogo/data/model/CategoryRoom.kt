package com.example.fogo.data.model

import com.example.fogo.R
import java.io.Serializable

class CategoryRoom(
    var id: String
): Serializable {

    fun getCityName() : String{
        return when(id){
            "1581130" -> "Ha Noi"
            "6058560" -> "London"
            "1580142" -> "Hung Yen"
            "1580410" -> "Ha Long"
            "1566346" -> "Thai Binh"
            else -> "CityName"
        }
    }

    fun getIconResID(): Int {
        return when (id) {
            "1581130" -> R.drawable.hanoi
            "6058560" -> R.drawable.london
            "1580142" -> R.drawable.item_3
            "1580410" -> R.drawable.halong
            "1566346" -> R.drawable.thaibinh
            else -> R.drawable.user1
        }
    }

}