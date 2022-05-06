package com.ahmed.currencyconverter.presentation.viewmodel

import android.util.AndroidException
import android.util.Log
import com.ahmed.currencyconverter.presentation.core.BaseViewModel
import com.ahmed.currencyconverter.presentation.core.wrapper.StateLiveData
import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.entities.CurrenciesDate
import com.ahmed.domain.entities.CurrencyConverterEntity
import com.ahmed.domain.entities.CurrencyEntities
import com.ahmed.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCurrencyConverter: GetCurrencyConverterUseCase,
    //private val getCurrencyConverterWithDate: GetCurrencyWithDateUseCase,
    private val getCurrenciesListWithDateUseCase: GetCurrenciesListWithDateUseCase,
    @Named("IO") private val ioScheduler: Scheduler,
    @Named("Main") private val mainScheduler: Scheduler
) : BaseViewModel() {

    val currencyLiveData by lazy { StateLiveData<List<CurrencyEntities>>() }
    val countriesLiveData by lazy { StateLiveData<List<CountriesEntities>>() }
    val currencyConverterLiveData by lazy { StateLiveData<CurrencyConverterEntity>() }
    val currencyListLiveData by lazy { StateLiveData<CurrenciesDate>() }
    fun getCurrency() {
        compositeDisposable.add(
            getCurrencyUseCase.invoke()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe { currencyLiveData.postLoading() }
                .subscribe({ response ->
                    currencyLiveData.postSuccess(response) },
                    { error ->
                        currencyLiveData.postError(error)
                        error.printStackTrace()
                    })
        )

    }

    fun getCountries() {
        compositeDisposable.add(
            getCountriesUseCase.invoke()
                .doOnSubscribe { countriesLiveData.postLoading() }
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe({ response ->
                    countriesLiveData.postSuccess(response)
                },
                    { error ->
                        countriesLiveData.postError(error)
                        error.printStackTrace()
                    })
        )
    }

    fun getCurrencyConverter(baseCurrency: String, secondCurrency: String) {
        compositeDisposable.add(
            getCurrencyConverter.invoke(baseCurrency, secondCurrency)
                .doOnSubscribe { currencyConverterLiveData.postLoading() }
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe({ response ->
                    currencyConverterLiveData.postSuccess(response)
                },
                    { error ->
                        currencyConverterLiveData.postError(error)
                        error.printStackTrace()
                    })
        )
    }



    fun getCurrencyListWithDate(baseCurrency: String, secondCurrency: String, lastDate : String, currentDate: String) {
        compositeDisposable.add(
            getCurrenciesListWithDateUseCase.invoke(baseCurrency, secondCurrency,lastDate,currentDate)
                .doOnSubscribe { currencyListLiveData.postLoading() }
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe({ response ->
                    currencyListLiveData.postSuccess(response)
                },
                    { error ->
                        currencyListLiveData.postError(error)
                        error.printStackTrace()
                    })
        )
    }

}