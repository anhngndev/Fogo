package com.example.fogo.data.model

import com.example.fogo.R
import java.io.Serializable

class Room
    (
    var roomType: RoomType,
    var address: String,
    val acreage: String,
    val price: String,
    val phone: String = "0868967660"
) : Serializable {


    fun getIconResID(): Int {
        return when (price.toInt() % 8) {
            0 -> R.drawable.item_1
            1 -> R.drawable.item_2
            2 -> R.drawable.item_3
            3 -> R.drawable.item_4
            4 -> R.drawable.item_8
            5 -> R.drawable.item_7
            6 -> R.drawable.item_6
            7 -> R.drawable.item_5
            else -> R.drawable.user1
        }
    }

    fun formatAddress() : String{
        return when(address){
            "Hanoi" -> "Ha Noi"
            "Hoian" -> "Hoi An"
            "Hungyen" -> "Hung Yen"
            "Halong" -> "Ha Long"
            "Thaibinh" -> "Thai Binh"
            else -> "CityName"
        }
    }

//    fun getRoomName(): String {
//        return when (roomType) {
//            RoomType.CLEAR -> R.string.CLEAR
//            RoomType.CLOUDS -> R.string.CLOUDS
//            RoomType.FOG -> R.string.FOG
//            RoomType.LIGHT_CLOUDS -> R.string.LIGHT_CLOUDS
//            RoomType.LIGHT_RAIN -> R.string.LIGHT_RAIN
//            RoomType.RAIN -> R.string.RAIN
//            RoomType.SNOW -> R.string.SNOW
//            RoomType.STORM -> R.string.STORM
//
//        }
//    }

    fun getRoomName(): String {
        return when (roomType) {
            RoomType.CLEAR -> "Clear motel"
            RoomType.CLOUDS -> "Cloud motel"
            RoomType.FOG -> "Fog apartment"
            RoomType.LIGHT_CLOUDS -> "Light cloud hotel"
            RoomType.LIGHT_RAIN -> "Light rain home"
            RoomType.RAIN -> "Rain home"
            RoomType.SNOW -> "Snow hotel"
            RoomType.STORM -> "Storm apartment"

        }
    }

//    fun getRoomAddress(): Int {
//        return when (roomType) {
//            RoomType.CLEAR -> R.string.HK
//            RoomType.CLOUDS -> R.string.HBT
//            RoomType.FOG -> R.string.HĐ
//            RoomType.LIGHT_CLOUDS -> R.string.HM
//            RoomType.LIGHT_RAIN -> R.string.CG
//            RoomType.RAIN -> R.string.BĐ
//            RoomType.SNOW -> R.string.DĐ
//            RoomType.STORM -> R.string.TX
//        }
//    }

    fun getRoomAddress(): String {
        return when (roomType) {
            RoomType.CLEAR -> "Hoan Kiem"
            RoomType.CLOUDS -> "Hai Ba Trung"
            RoomType.FOG -> "Ha Dong"
            RoomType.LIGHT_CLOUDS -> "Hoang Mai"
            RoomType.LIGHT_RAIN -> "Cau Giay"
            RoomType.RAIN -> "Ba Dinh"
            RoomType.SNOW -> "Dong Da"
            RoomType.STORM -> "Thanh Xuan"
        }
    }


}