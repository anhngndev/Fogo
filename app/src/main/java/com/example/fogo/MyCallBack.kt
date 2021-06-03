package com.example.fogo

interface MyCallBack {
    fun onSuccess(result: String?)
    fun onFailed(error: Exception?)
}