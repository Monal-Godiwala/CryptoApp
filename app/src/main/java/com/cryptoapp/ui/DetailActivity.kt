package com.cryptoapp.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cryptoapp.R
import com.cryptoapp.databinding.ActivityDetailBinding
import com.cryptoapp.network.ApiClient
import com.cryptoapp.network.ApiService
import com.cryptoapp.repository.Repository
import com.cryptoapp.ui.viewmodel.CryptoViewModel
import com.cryptoapp.ui.viewmodel.CryptoViewModelFactory
import com.cryptoapp.util.AppUtils
import com.cryptoapp.util.AppUtils.convertLongToDate
import com.cryptoapp.util.Constant.KEY_SYMBOL

class DetailActivity : AppCompatActivity() {

    lateinit var dataBind: ActivityDetailBinding

    private var apiService: ApiService? =
        ApiClient.getClient()?.create(ApiService::class.java)
    private val repository = Repository(apiService)
    private val factory = CryptoViewModelFactory(repository)
    private val viewModel: CryptoViewModel by lazy {
        ViewModelProvider(this, factory)[CryptoViewModel::class.java]
    }

    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 2000

    var symbol: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        symbol = intent.getStringExtra(KEY_SYMBOL)

        bindData()

    }

    override fun onResume() {
        handler.postDelayed(Runnable {
            runnable?.let {
                handler.postDelayed(it, delay.toLong())
                viewModel.getCryptoDetail(symbol) { error ->
//                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                }
            }
        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        runnable?.let { handler.removeCallbacks(it) }
    }

    private fun bindData() {

        dataBind.progressLastPrice.isEnabled = false

        viewModel.cryptoDetail.observe(this) { currency ->

            dataBind.loader.visibility = View.GONE

            dataBind.textName.text = currency?.baseAsset?.uppercase()
            dataBind.textLastPrice.text = AppUtils.currencyFormat(currency?.lastPrice)
            dataBind.textLowPrice.text = AppUtils.currencyFormat(currency?.lowPrice)
            dataBind.textHighPrice.text = AppUtils.currencyFormat(currency?.highPrice)
            dataBind.textHighPrice.text = AppUtils.currencyFormat(currency?.highPrice)
            dataBind.textOpenPrice.text = AppUtils.currencyFormat(currency?.openPrice)
            dataBind.textVolumeCount.text = currency?.volume
            dataBind.textTime.text = "at ${currency?.at?.convertLongToDate()}"

            dataBind.textBidPrice.text = AppUtils.currencyFormat(currency?.bidPrice)
            dataBind.textAskPrice.text = AppUtils.currencyFormat(currency?.askPrice)

            dataBind.progressLastPrice.min = currency?.lowPrice?.toDoubleOrNull()?.toInt() ?: 0
            dataBind.progressLastPrice.max = currency?.highPrice?.toDoubleOrNull()?.toInt() ?: 0
            dataBind.progressLastPrice.progress =
                currency?.lastPrice?.toDoubleOrNull()?.toInt() ?: 0

        }
    }
}