package com.cryptoapp.network

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.cryptoapp.util.AppUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor(context: Context?) :
    Interceptor {

    private val applicationContext = context?.applicationContext

    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!AppUtils.isNetworkAvailable(applicationContext))
            throw IOException("Please check your Network Connection")
        return chain.proceed(chain.request())
    }
}