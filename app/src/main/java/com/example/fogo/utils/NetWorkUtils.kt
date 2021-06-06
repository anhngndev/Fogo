package com.example.fogo.utils

import android.util.Log
import com.example.fogo.data.model.CategoryRoom
import com.example.fogo.data.model.Room
import com.example.fogo.data.model.RoomType
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.roundToInt

 class NetWorkUtils {

    companion object {
        @JvmStatic
        fun jsonToRoomList(json: String?): MutableList<Room> {
            val list: MutableList<Room> = mutableListOf()
            try {
                val jsonObject = JSONObject(json)

                val cityInformation = jsonObject.getJSONObject("city")
                val cityName = cityInformation.getString("name")

                val roomList = jsonObject.getJSONArray("list")
                Log.e("Room List", roomList.length().toString() + " ")
                for (i in 0 until roomList.length()) {
                    val `object` = roomList.getJSONObject(i)
                    val object1 = `object`.getJSONObject("temp")
                    val price = getFormattedPrice(object1.getDouble("max"))
                    val acreage = getFormattedAcreage(object1.getDouble("min"))
                    val roomArrayList = `object`.getJSONArray("weather")
                    val roomObject = roomArrayList.getJSONObject(0)
                    val roomType: RoomType =
                        RoomType.valueOf(roomObject.getString("main").toUpperCase())
                    val roomInformation = Room(roomType, cityName, acreage, price, "0868967660")
                    list.add(roomInformation)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return list
        }

        @JvmStatic
        fun getCityFromJson(json: String?): String {
            val jsonObject = JSONObject(json)
            val cityInformation = jsonObject.getJSONObject("city")
            return cityInformation.getString("name")
        }

        fun jsonToRoomCategory(json: String?): MutableList<CategoryRoom> {
            val list: MutableList<CategoryRoom> = mutableListOf()
            val item = CategoryRoom("1581130")
            val item1 = CategoryRoom("6058560")
            val item2 = CategoryRoom("1580142")
            val item3 = CategoryRoom("1580410")
            val item4 = CategoryRoom("1566346")
            list.add(item)
            list.add(item1)
            list.add(item2)
            list.add(item3)
            list.add(item4)
            return list
        }


        fun getFormattedPrice(temp: Double): String {
            return (temp * 100).roundToInt().toString()
        }

        fun getFormattedAcreage(temp: Double): String {
            return (temp - 220).roundToInt().toString()
        }


    }


}