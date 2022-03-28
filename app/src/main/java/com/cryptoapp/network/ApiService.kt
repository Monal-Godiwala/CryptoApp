package com.cryptoapp.network

import com.cryptoapp.data.ExchangeInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("sapi/v1/tickers/24hr")
    suspend fun getExchangeInfoList(): Response<ArrayList<ExchangeInfo>>

    @GET("sapi/v1/ticker/24hr")
    suspend fun getSymbolisedExchangeInfo(@Query("symbol") symbol: String): Response<ExchangeInfo>

}