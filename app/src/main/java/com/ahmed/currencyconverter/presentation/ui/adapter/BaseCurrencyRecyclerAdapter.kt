package com.ahmed.currencyconverter.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.currencyconverter.R
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.android.synthetic.main.currency_item.view.*
import java.text.NumberFormat
import javax.inject.Inject

class BaseCurrencyRecyclerAdapter @Inject constructor (
    @ActivityContext private val context: Context
) : RecyclerView.Adapter<BaseCurrencyRecyclerAdapter.ViewHolder>() {
    private var currencyDateList = ArrayList<String>()
    private var currencyList = ArrayList<String>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseCurrencyRecyclerAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.currency_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseCurrencyRecyclerAdapter.ViewHolder, position: Int) {
        val mNumberFormat = NumberFormat.getInstance()
        mNumberFormat.maximumFractionDigits = 2

        holder.currencyDateTV.text = currencyDateList[position]
        holder.currencyTV.text = mNumberFormat.format(currencyList[position].toDouble()).toString()
    }

    fun setCurrencyDateList(currencyDateList:ArrayList<String>){
        this.currencyDateList = currencyDateList
    }
    fun setCurrencyList(currencyList:ArrayList<String>){
        this.currencyList = currencyList
    }

    override fun getItemCount() = currencyDateList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        var currencyDateTV: TextView = view.date_currency_tv
        var currencyTV: TextView = view.currency_tv
    }

}