package com.cryptoapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import java.text.SimpleDateFormat

object AppUtils {

    fun isNetworkAvailable(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.activeNetwork
        } else {
            cm.activeNetworkInfo
        }
        return activeNetwork != null
    }

    fun currencyFormat(amount: String?): String {
        return "₹${String.format("%.2f", amount?.toDouble())}"
    }

    @SuppressLint("SimpleDateFormat")
    fun Long.convertLongToDate(): String? {
        return SimpleDateFormat("hh:mm:ss a").format(this)
    }

}