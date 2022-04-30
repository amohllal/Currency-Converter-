package com.ahmed.currencyconverter.presentation.viewmodel

import android.util.Log
import com.ahmed.currencyconverter.presentation.core.BaseViewModel
import com.ahmed.currencyconverter.presentation.core.wrapper.StateLiveData
import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.entities.CurrencyEntities
import com.ahmed.domain.usecase.GetCountriesUseCase
import com.ahmed.domain.usecase.GetCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    @Named("IO") private val ioScheduler: Scheduler,
    @Named("Main") private val mainScheduler: Scheduler
) : BaseViewModel() {

    val currencyLiveData by lazy { StateLiveData<List<CurrencyEntities>>() }
    val countriesLiveData by lazy { StateLiveData<List<CountriesEntities>>() }

    fun getCurrency() {
        compositeDisposable.add(
            getCurrencyUseCase.invoke()
                .doOnSubscribe { currencyLiveData.postLoading() }
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe({ response -> currencyLiveData.postSuccess(response)},
                    { error -> currencyLiveData.postError(error)
                    error.printStackTrace()})
        )
    }
    fun getCountries() {
        compositeDisposable.add(
            getCountriesUseCase.invoke()
                .doOnSubscribe { countriesLiveData.postLoading() }
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe({ response ->
                    countriesLiveData.postSuccess(response)},
                    { error -> countriesLiveData.postError(error)
                    error.printStackTrace()})
        )
    }

}