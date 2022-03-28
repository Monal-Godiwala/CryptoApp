package com.cryptoapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cryptoapp.R
import com.cryptoapp.data.ExchangeInfo
import com.cryptoapp.databinding.ActivityMainBinding
import com.cryptoapp.network.ApiClient
import com.cryptoapp.network.ApiService
import com.cryptoapp.repository.Repository
import com.cryptoapp.ui.viewmodel.CryptoViewModel
import com.cryptoapp.ui.viewmodel.CryptoViewModelFactory
import com.cryptoapp.util.Constant

class MainActivity : AppCompatActivity() {

    lateinit var dataBind: ActivityMainBinding

    private var apiService: ApiService? =
        ApiClient.getClient()?.create(ApiService::class.java)
    private val repository = Repository(apiService)
    private val factory = CryptoViewModelFactory(repository)
    private val viewModel: CryptoViewModel by lazy {
        ViewModelProvider(this, factory)[CryptoViewModel::class.java]
    }

    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 3000

    private var cryptoAdapter: CryptoAdapter? = null
    private val currencyList = ArrayList<ExchangeInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initViews()

        bindData()

    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(Runnable {
            runnable?.let {
                handler.postDelayed(it, delay.toLong())
                viewModel.getCryptoList {}
            }
        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        runnable?.let { handler.removeCallbacks(it) }
    }

    private fun initViews() {

        cryptoAdapter = CryptoAdapter(currencyList) { position ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(Constant.KEY_SYMBOL, currencyList[position].symbol)
            startActivity(intent)
        }

        dataBind.listCrypto.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = cryptoAdapter
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun bindData() {
        viewModel.getCryptoList { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }

        viewModel.cryptoList.observe(this) { list ->
            list?.let {

                dataBind.loader.visibility = View.GONE

                currencyList.addAll(it)
                cryptoAdapter?.notifyDataSetChanged()

            }
        }
    }
}