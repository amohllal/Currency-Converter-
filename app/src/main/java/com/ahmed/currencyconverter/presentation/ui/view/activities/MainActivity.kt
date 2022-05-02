package com.ahmed.currencyconverter.presentation.ui.view.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ahmed.currencyconverter.R
import com.ahmed.currencyconverter.databinding.ActivityMainBinding
import com.ahmed.currencyconverter.presentation.core.Connectivity
import com.ahmed.currencyconverter.presentation.core.wrapper.DataStatus
import com.ahmed.currencyconverter.presentation.viewmodel.CurrencyViewModel
import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.entities.CurrencyEntities
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val currencyViewModel: CurrencyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getCurrenciesAndCountries()
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getCurrenciesAndCountries(){
        if (Connectivity.isOnline(this)){
            currencyViewModel.getRemoteCurrency()
            currencyViewModel.getRemoteCountries()
        }else{
            currencyViewModel.getLocalCurrency()
            currencyViewModel.getLocalCountries()
        }
        observeOnCurrencies()
        observeOnCountries()
    }
    private fun observeOnCurrencies(){
        currencyViewModel.currencyLiveData.observe(this) {
                when(it?.status){
                    DataStatus.Status.LOADING ->showProgressLoading()
                    DataStatus.Status.SUCCESS ->handleCurrenciesSuccessData(it.data)
                    DataStatus.Status.ERROR ->handleError()
                    else -> {}
                }
        }
    }

    private fun handleCurrenciesSuccessData(data: List<CurrencyEntities>?) {
        hideProgressLoading()

    }

    private fun showProgressLoading(){
        binding.contentLayout.isVisible = false
        binding.progressLoading.isVisible = true


    }
    private fun hideProgressLoading(){
        binding.progressLoading.isVisible = false
        binding.contentLayout.isVisible = true


    }
    private fun handleError(){
        binding.progressLoading.isVisible = false
        Toast.makeText(this,"Something happens please try again!",Toast.LENGTH_LONG).show()
    }

    private fun observeOnCountries(){
        currencyViewModel.countriesLiveData.observe(this) {
                if (it?.status == DataStatus.Status.SUCCESS){
                    handleCountriesSuccessData(it.data)
                }else if (it?.status == DataStatus.Status.ERROR){
                    handleError()
                }
        }
    }

    private fun handleCountriesSuccessData(data: List<CountriesEntities>?) {

    }

   /* @SuppressLint("SimpleDateFormat")
    private fun getDate() {
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        calendar.add(Calendar.DAY_OF_WEEK, -7)
        val strDate = simpleDateFormat.format(calendar.time)
    }*/


}