package com.cyberwalker.fashionstore.util

import android.content.Context
import android.widget.Toast


data class LoadingState private constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val IDLE = LoadingState(Status.IDLE)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        IDLE,
    }
}



fun showMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}