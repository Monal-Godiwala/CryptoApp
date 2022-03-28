package com.cryptoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cryptoapp.data.ExchangeInfo
import com.cryptoapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoViewModel(private val repository: Repository) : ViewModel() {

    private val _cryptoList = MutableLiveData<ArrayList<ExchangeInfo>?>()
    val cryptoList: LiveData<ArrayList<ExchangeInfo>?>
        get() = _cryptoList

    private val _cryptoDetail = MutableLiveData<ExchangeInfo?>()
    val cryptoDetail: LiveData<ExchangeInfo?>
        get() = _cryptoDetail

    fun getCryptoList(onFailure: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getExchangeInfoList()
                withContext(Dispatchers.Main) {
                    _cryptoList.value = response
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let {
                        _cryptoList.value = null
                        onFailure(it)
                    }
                }
            }
        }
    }

    fun getCryptoDetail(symbol: String?, onFailure: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = symbol?.let { repository.getSymbolisedExchangeInfo(it) }
                withContext(Dispatchers.Main) {
                    _cryptoDetail.value = response
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let {
                        _cryptoDetail.value = null
                        onFailure(it)
                    }
                }
            }
        }
    }

}