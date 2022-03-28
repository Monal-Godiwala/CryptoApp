package com.cryptoapp.data


import com.google.gson.annotations.SerializedName

data class ExchangeInfo(
    @SerializedName("askPrice")
    var askPrice: String? = null,
    @SerializedName("at")
    var at: Long? = null,
    @SerializedName("baseAsset")
    var baseAsset: String? = null,
    @SerializedName("bidPrice")
    var bidPrice: String? = null,
    @SerializedName("highPrice")
    var highPrice: String? = null,
    @SerializedName("lastPrice")
    var lastPrice: String? = null,
    @SerializedName("lowPrice")
    var lowPrice: String? = null,
    @SerializedName("openPrice")
    var openPrice: String? = null,
    @SerializedName("quoteAsset")
    var quoteAsset: String? = null,
    @SerializedName("symbol")
    var symbol: String? = null,
    @SerializedName("volume")
    var volume: String? = null
)
