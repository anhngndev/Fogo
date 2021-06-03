package com.example.fogo.data.model

import com.example.fogo.R

class CategoryRoom(
    var id: String
) {

    fun getCityName() : String{
        return when(id){
            "1581130" -> "Ha Noi"
            "1580541" -> "Hoi An"
            "1580142" -> "Hung Yen"
            "1580410" -> "Ha Long"
            "1566346" -> "Thai Binh"
            else -> "CityName"
        }
    }

    fun getIconResID(): Int {
        return when (id) {
            "1581130" -> R.drawable.item_1
            "1580541" -> R.drawable.item_2
            "1580142" -> R.drawable.item_3
            "1580410" -> R.drawable.item_4
            "1566346" -> R.drawable.item_8
            else -> R.drawable.user1
        }
    }

}