package com.example.moviecatalogue.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class ConnectivityReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener!!.onNetworkConnectionChanged(isConnectedOrConnecting(context!!))
        }

    }

    private fun isConnectedOrConnecting(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        val nc: NetworkCapabilities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connMgr.getNetworkCapabilities(connMgr.activeNetwork)
        } else {
            return false
        }
        val downSpeed: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            nc.linkDownstreamBandwidthKbps
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }
        val upSpeed: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            nc.linkUpstreamBandwidthKbps
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }

        return networkInfo != null && networkInfo.isConnectedOrConnecting && downSpeed > 10000 && upSpeed > 10000
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }
}