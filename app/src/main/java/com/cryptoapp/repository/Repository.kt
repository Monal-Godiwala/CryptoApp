package com.cryptoapp.repository

import com.cryptoapp.data.ExchangeInfo
import com.cryptoapp.network.ApiService
import com.cryptoapp.network.SafeApiRequest

class Repository(private val apiService: ApiService?) : SafeApiRequest() {

    suspend fun getExchangeInfoList(): ArrayList<ExchangeInfo> =
        apiRequest { apiService?.getExchangeInfoList() }

    suspend fun getSymbolisedExchangeInfo(symbol: String): ExchangeInfo =
        apiRequest { apiService?.getSymbolisedExchangeInfo(symbol) }

}