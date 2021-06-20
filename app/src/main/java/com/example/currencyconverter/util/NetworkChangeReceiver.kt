package com.example.currencyconverter.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.net.InetAddress


class NetworkChangeReceiver() : BroadcastReceiver() {

    private var listener: Receiver? = null

    override fun onReceive(context: Context, intent: Intent?) {
        listener = context as Receiver
        listener!!.letMeKnowWhenNetworkStateChanged(checkInternet(context))
    }

    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            if (ipAddr.equals("")) {
                false
            } else {
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    fun checkInternet(context: Context): Boolean {
        val serviceManager = ServiceManager(context);
        return serviceManager.isNetworkAvailable()
    }
}