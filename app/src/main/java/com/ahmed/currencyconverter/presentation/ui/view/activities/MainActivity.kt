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
import com.ahmed.currencyconverter.presentation.ui.adapter.BaseCurrencyRecyclerAdapter
import com.ahmed.currencyconverter.presentation.ui.adapter.SecondCurrencyRecyclerAdapter
import com.ahmed.currencyconverter.presentation.ui.dialog.CustomProgressDialog
import com.ahmed.currencyconverter.presentation.viewmodel.CurrencyViewModel
import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.entities.CurrenciesDate
import com.ahmed.domain.entities.CurrencyConverterEntity
import com.ahmed.domain.entities.CurrencyEntities
import com.squareup.picasso.Picasso
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
    private val countryFlagUrl = "https://flagcdn.com/32x24/"

    @Inject
    lateinit var customProgressLoading: CustomProgressDialog

    @Inject
    lateinit var baseRecyclerAdapter: BaseCurrencyRecyclerAdapter

    @Inject
    lateinit var secondRecyclerAdapter: SecondCurrencyRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCurrenciesAndCountries()
        initViews()
    }

    private fun initViews() {
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
                                setBaseCurrencyDataToViews(it.name, it.currencyName, it.id)

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
                                setSecondCurrencyDataToViews(it.name, it.currencyName, it.id)
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

    private fun setSecondCurrencyDataToViews(
        name: String,
        currencyName: String,
        countryId: String
    ) {
        binding.secondCountryTitleTv.text = name
        binding.secondCurrencyNameTv.text = currencyName
        Picasso.get()
            .load(countryFlagUrl.plus(countryId.lowercase().plus(".png")))
            .into(binding.secondCountryFlagIv)

    }

    private fun setBaseCurrencyDataToViews(name: String, currencyName: String, countryId: String) {
        binding.baseCountryTitleTv.text = name
        binding.baseCurrencyNameTv.text = currencyName
        Picasso.get()
            .load(countryFlagUrl.plus(countryId.lowercase().plus(".png")))
            .into(binding.baseCountryFlagIv)
    }

    private fun getCurrenciesAndCountries() {
        currencyViewModel.getCurrency()
        currencyViewModel.getCountries()

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
        if (data.isNullOrEmpty()){
            Toast.makeText(this, "Something happens please try again!", Toast.LENGTH_LONG).show()
            binding.contentLayout.isVisible = false
        }else{
            currencyList.addAll(data)
            initBaseCurrencySpinner()
            initSecondCurrencySpinner()
        }

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
        if (data.isNullOrEmpty()){
            binding.contentLayout.isVisible = false
        }else{
            countryList.addAll(data)
        }

    }

    private fun validation(): Boolean {
        if (selectedBaseCurrency.isNullOrEmpty() || selectedSecondCurrency.isNullOrEmpty()) {
            Toast.makeText(this, "You must select your currencies", Toast.LENGTH_LONG).show()
            return false
        }
        if (selectedBaseCurrency == selectedSecondCurrency) {
            Toast.makeText(this, "You must select you different currencies", Toast.LENGTH_LONG)
                .show()
            return false
        }

        return true

    }

    private fun hitCurrencyConverterAPI() {
        if (validation()) {
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
                currencyViewModel.getCurrencyConverter(baseCurrency, secondCurrency)
                observeOnCurrencyConverter()
            }
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

    private fun hitCurrencyWithDateAPI(date: Pair<String, String>) {
        if (validation()) {
            baseCurrency = selectedBaseCurrency.plus("_").plus(selectedSecondCurrency)
            secondCurrency = selectedSecondCurrency.plus("_").plus(selectedBaseCurrency)
            currencyViewModel.getCurrencyListWithDate(
                baseCurrency, secondCurrency,
                date.first, date.second
            )
            observeOnCurrencyDateList()

        }
    }

    private fun observeOnCurrencyDateList() {
        currencyViewModel.currencyListLiveData.observe(this) {
            when (it?.status) {
                DataStatus.Status.LOADING -> showConverterLoading()
                DataStatus.Status.SUCCESS -> handleCurrencyListSuccess(it.data)
                DataStatus.Status.ERROR -> handleConverterError()
                else -> {}
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleCurrencyListSuccess(data: CurrenciesDate?) {
        hideConverterLoading()
        binding.baseCurrencyTv.text = baseCurrency
        binding.secondCurrencyTv.text = secondCurrency
        baseRecyclerAdapter.setCurrencyDateList(data?.baseCurrencyEntity?.date as ArrayList<String>)
        baseRecyclerAdapter.setCurrencyList(data.baseCurrencyEntity.currency as ArrayList<String>)
        secondRecyclerAdapter.setCurrencyDateList(data.secondCurrencyEntity.date as ArrayList<String>)
        secondRecyclerAdapter.setCurrencyList(data.secondCurrencyEntity.currency as ArrayList<String>)
        binding.firstCurrencyRv.adapter = baseRecyclerAdapter
        binding.secondCurrencyRv.adapter = secondRecyclerAdapter
        baseRecyclerAdapter.notifyDataSetChanged()
        secondRecyclerAdapter.notifyDataSetChanged()

    }

    private fun handleSuccessConverter(data: CurrencyConverterEntity?) {
        hideConverterLoading()
        setCurrencyConverterDataToViews(data)

    }

    private fun setCurrencyConverterDataToViews(data: CurrencyConverterEntity?) {
        val mNumberFormat = NumberFormat.getInstance()
        mNumberFormat.maximumFractionDigits = 2
        if (binding.baseCurrencyEt.text.isNotEmpty()) {
            binding.secondCurrencyEt.setText(
                (mNumberFormat.format(
                    data?.baseCurrency?.toDouble()
                        ?.times(binding.baseCurrencyEt.text.toString().toDouble())
                ).toString())
            )
        } else if (binding.secondCurrencyEt.text.isNotEmpty()) {
            binding.baseCurrencyEt.setText(
                (mNumberFormat.format(
                    binding.secondCurrencyEt.text.toString().toDouble()
                        .div(data?.baseCurrency?.toDouble()!!)
                ).toString())
            )
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
    private fun getDate(): Pair<String, String> {
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = simpleDateFormat.format(calendar.time)
        calendar.add(Calendar.DAY_OF_WEEK, -7)
        val lastDate = simpleDateFormat.format(calendar.time)
        return Pair(lastDate, currentDate)
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.sync_btn -> hitCurrencyConverterAPI()
            R.id.sync_date_btn -> hitCurrencyWithDateAPI(getDate())
        }
    }


}