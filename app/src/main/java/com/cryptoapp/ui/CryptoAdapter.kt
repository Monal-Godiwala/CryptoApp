package com.cryptoapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cryptoapp.R
import com.cryptoapp.data.ExchangeInfo
import com.cryptoapp.databinding.ListItemCryptoBinding
import com.cryptoapp.util.AppUtils
import java.util.*
import kotlin.collections.ArrayList


class CryptoAdapter(
    private val currencies: ArrayList<ExchangeInfo>,
    private val currencyListener: (Int) -> Unit,
) : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        return CryptoViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_crypto,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = currencies.size

    override fun onBindViewHolder(holder: CryptoAdapter.CryptoViewHolder, position: Int) {
        holder.bindItems(currencies[position])
        holder.itemView.setOnClickListener { currencyListener.invoke(position) }
    }

    inner class CryptoViewHolder(private val dataBind: ListItemCryptoBinding) :
        RecyclerView.ViewHolder(dataBind.root) {

        @SuppressLint("SetTextI18n")
        fun bindItems(currency: ExchangeInfo) {
            dataBind.apply {
                textName.text = currency.baseAsset?.uppercase()
                textPrice.text = AppUtils.currencyFormat(currency.lastPrice)
            }

        }

    }

}