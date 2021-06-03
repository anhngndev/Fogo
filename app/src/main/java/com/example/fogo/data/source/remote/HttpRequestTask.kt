package com.example.fogo.data.source.remote

import android.os.AsyncTask
import android.util.Log
import com.example.fogo.MyCallBack
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpRequestTask(
    private val requestMethod: HttpRequestMethod,
    private val callback: Callback
) :
    AsyncTask<String?, Void?, Void?>() {
    private var result: String? = null
    private var error: Exception? = null

    interface Callback {
        fun onSuccess(result: String?)
        fun onFailed(error: Exception?)
    }

    override fun doInBackground(vararg p0: String?): Void? {
        val stringUrl = p0[0]
        Log.e("HttpRequestTask", "String Url $stringUrl")
        try {
            val connection = generateHttpRequest(stringUrl)
            connection.connect()
            result = readFromResponse(connection)
            Log.e("HttpRequestTask", result + "")
        } catch (e: Exception) {
            error = e
            Log.e("HttpRequestTask", "result error$e")
        }
        return null
    }

    @Throws(Exception::class)
    private fun generateHttpRequest(stringUrl: String?): HttpURLConnection {
        val url = URL(stringUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = requestMethod.toString()
        connection.readTimeout = 10000
        connection.connectTimeout = 10000
        return connection
    }

    @Throws(IOException::class)
    private fun readFromResponse(connection: HttpURLConnection): String {
        var inputLine: String?

        //Create a new InputStreamReader
        val inputStreamReader = InputStreamReader(connection.inputStream)

        //Create a new buffered reader and String Builder
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder = StringBuilder()

        //Check if the line we are reading is not null
        while (bufferedReader.readLine().also { inputLine = it } != null) {
            stringBuilder.append(inputLine)
        }
        bufferedReader.close()
        inputStreamReader.close()
        return stringBuilder.toString()
    }

    public override fun onPostExecute(aVoid: Void?) {
        super.onPostExecute(aVoid)
        if (result != null) {
            Log.e("Get data", "Success")
            callback.onSuccess(result)
        }
        if (error != null) {
            Log.e("Get data", "Failed")
            callback.onFailed(error)
        }
    }


}
