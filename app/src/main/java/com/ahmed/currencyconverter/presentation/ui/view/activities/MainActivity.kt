package com.ahmed.currencyconverter.presentation.ui.view.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ahmed.currencyconverter.R
import com.ahmed.currencyconverter.databinding.ActivityMainBinding
import com.ahmed.currencyconverter.presentation.core.Connectivity
import com.ahmed.currencyconverter.presentation.core.wrapper.DataStatus
import com.ahmed.currencyconverter.presentation.ui.dialog.CustomProgressDialog
import com.ahmed.currencyconverter.presentation.viewmodel.CurrencyViewModel
import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.entities.CurrencyConverterEntity
import com.ahmed.domain.entities.CurrencyEntities
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val currencyViewModel: CurrencyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var currencyList = ArrayList<CurrencyEntities>()
    private var countryList = ArrayList<CountriesEntities>()
    private var selectedBaseCurrency: String? = null
    private var selectedSecondCurrency: String? = null
    private lateinit var baseCurrency: String
    private lateinit var secondCurrency: String

    @Inject
    lateinit var customProgressLoading: CustomProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getCurrenciesAndCountries()
        }
       initViews()
        currencyViewModel.getCurrencyListWithDate("USD_PHP","PHP_USD","2022-4-25","2022-5-2")
    }

    private fun initViews(){
        binding.syncBtn.setOnClickListener(this)
        binding.syncDateBtn.setOnClickListener(this)
    }

    private fun initBaseCurrencySpinner() {
        val currencyEntity = CurrencyEntities("", "", "Select")
        currencyList[0] = currencyEntity
        val categoryAdapter: ArrayAdapter<CurrencyEntities> =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.baseCurrencySpinner.adapter = categoryAdapter
        binding.baseCurrencySpinner.setSelection(0)
        binding.baseCurrencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (binding.baseCurrencySpinner.selectedItemPosition != 0) {
                        selectedBaseCurrency = currencyList[parent!!.selectedItemPosition].id
                        countryList.forEach {
                            if (it.currencyId == selectedBaseCurrency) {
                                setBaseCurrencyDataToViews(it.name, it.currencyName)

                            }
                        }

                    } else {
                        selectedBaseCurrency = null
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

    }


    private fun initSecondCurrencySpinner() {
        val currencyEntity = CurrencyEntities("", "", "Select")
        currencyList[0] = currencyEntity
        val adapter: ArrayAdapter<CurrencyEntities> =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.secondCurrencySpinner.adapter = adapter
        binding.secondCurrencySpinner.setSelection(0)
        binding.secondCurrencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (binding.secondCurrencySpinner.selectedItemPosition != 0) {
                        selectedSecondCurrency = currencyList[parent!!.selectedItemPosition].id
                        countryList.forEach {
                            if (it.currencyId == selectedSecondCurrency) {
                                setSecondCurrencyDataToViews(it.name, it.currencyName)
                            }
                        }

                    } else {
                        selectedSecondCurrency = null

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

    }

    private fun setSecondCurrencyDataToViews(name: String, currencyName: String) {
        binding.secondCountryTitleTv.text = name
        binding.secondCurrencyNameTv.text = currencyName

    }

    private fun setBaseCurrencyDataToViews(name: String, currencyName: String) {
        binding.baseCountryTitleTv.text = name
        binding.baseCurrencyNameTv.text = currencyName
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getCurrenciesAndCountries() {
        if (Connectivity.isOnline(this)) {
            currencyViewModel.getRemoteCurrency()
            currencyViewModel.getRemoteCountries()
        } else {
            currencyViewModel.getLocalCurrency()
            currencyViewModel.getLocalCountries()
        }
        observeOnCurrenciesList()
        observeOnCountries()

    }

    private fun observeOnCurrenciesList() {
        currencyViewModel.currencyLiveData.observe(this) {
            when (it?.status) {
                DataStatus.Status.LOADING -> showProgressLoading()
                DataStatus.Status.SUCCESS -> handleCurrenciesListSuccessData(it.data)
                DataStatus.Status.ERROR -> handleError()
                else -> {}
            }
        }
    }

    private fun handleCurrenciesListSuccessData(data: List<CurrencyEntities>?) {
        hideProgressLoading()
        currencyList.addAll(data ?: emptyList())
        initBaseCurrencySpinner()
        initSecondCurrencySpinner()

    }

    private fun showProgressLoading() {
        binding.contentLayout.isVisible = false
        binding.progressLoading.isVisible = true


    }

    private fun hideProgressLoading() {
        binding.progressLoading.isVisible = false
        binding.contentLayout.isVisible = true


    }

    private fun handleError() {
        binding.progressLoading.isVisible = false
        Toast.makeText(this, "Something happens please try again!", Toast.LENGTH_LONG).show()
    }

    private fun observeOnCountries() {
        currencyViewModel.countriesLiveData.observe(this) {
            if (it?.status == DataStatus.Status.SUCCESS) {
                handleCountriesSuccessData(it.data)
            } else if (it?.status == DataStatus.Status.ERROR) {
                handleError()
            }
        }
    }

    private fun handleCountriesSuccessData(data: List<CountriesEntities>?) {
        countryList.addAll(data ?: emptyList())

    }

    private fun hitCurrencyConverterAPI(date: String?) {
        if (selectedBaseCurrency.isNullOrEmpty() || selectedSecondCurrency.isNullOrEmpty()) {
            Toast.makeText(this, "You must select your currencies", Toast.LENGTH_LONG).show()
            return
        }
        if (selectedBaseCurrency == selectedSecondCurrency) {
            Toast.makeText(this, "You must select you different currencies", Toast.LENGTH_LONG)
                .show()
            return
        }

        if (binding.baseCurrencyEt.text.isNullOrEmpty() && binding.secondCurrencyEt.text.isNullOrEmpty()) {
            Toast.makeText(this, "You must insert any currency value", Toast.LENGTH_LONG).show()
            return

        }
        if (binding.secondCurrencyEt.text.isNotEmpty() && binding.baseCurrencyEt.text.isNotEmpty()) {
            Toast.makeText(
                this,
                "You must insert maximum one value at any field",
                Toast.LENGTH_LONG
            ).show()
            binding.secondCurrencyEt.text.clear()
            binding.baseCurrencyEt.text.clear()

        } else {
            baseCurrency = selectedBaseCurrency.plus("_").plus(selectedSecondCurrency)
            secondCurrency = selectedSecondCurrency.plus("_").plus(selectedBaseCurrency)

            if (date.isNullOrEmpty()) {
                currencyViewModel.getCurrencyConverter(baseCurrency, secondCurrency)
            } else {
                currencyViewModel.getCurrencyConverterWithDate(baseCurrency, secondCurrency, date)
            }
            observeOnCurrencyConverter()
        }


    }

    private fun observeOnCurrencyConverter() {
        currencyViewModel.currencyConverterLiveData.observe(this) {
            when (it?.status) {
                DataStatus.Status.LOADING -> showConverterLoading()
                DataStatus.Status.SUCCESS -> handleSuccessConverter(it.data)
                DataStatus.Status.ERROR -> handleConverterError()
                else -> {}
            }
        }
    }

    private fun handleSuccessConverter(data: CurrencyConverterEntity?) {
        hideConverterLoading()
        setCurrencyConverterDataToViews(data)

    }

    private fun setCurrencyConverterDataToViews(data: CurrencyConverterEntity?) {
        val mNumberFormat = NumberFormat.getInstance()
        mNumberFormat.maximumFractionDigits = 2
            if (binding.baseCurrencyEt.text.isNotEmpty()){
                binding.secondCurrencyEt.setText((mNumberFormat.format(data?.baseCurrency?.toDouble()
                    ?.times(binding.baseCurrencyEt.text.toString().toDouble())).toString()))
            }else if (binding.secondCurrencyEt.text.isNotEmpty()){
                binding.baseCurrencyEt.setText((mNumberFormat.format(binding.secondCurrencyEt.text.toString().toDouble().div(data?.baseCurrency?.toDouble()!!)).toString()))
            }
    }

    private fun handleConverterError() {
        hideConverterLoading()
        Toast.makeText(this, "some error happens please try again!", Toast.LENGTH_LONG).show()

    }

    private fun showConverterLoading() {
        customProgressLoading.showCustomDialog(false)
    }

    private fun hideConverterLoading() {
        customProgressLoading.dismissCustomDialog()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(): String {
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        calendar.add(Calendar.DAY_OF_WEEK, -7)
        return simpleDateFormat.format(calendar.time)
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.sync_btn -> hitCurrencyConverterAPI(null)
            R.id.sync_date_btn -> hitCurrencyConverterAPI(getDate())
        }
    }


}