package com.ahmed.currencyconverter.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ahmed.currencyconverter.presentation.core.wrapper.DataStatus
import com.ahmed.domain.entities.*
import com.ahmed.domain.usecase.GetCountriesUseCase
import com.ahmed.domain.usecase.GetCurrenciesListWithDateUseCase
import com.ahmed.domain.usecase.GetCurrencyConverterUseCase
import com.ahmed.domain.usecase.GetCurrencyUseCase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrencyViewModelTest{

    //swaps the background executor used by the Architecture Components
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var currencyObserver: Observer<DataStatus<List<CurrencyEntities>>?>
    @Mock
    lateinit var countriesObserver: Observer<DataStatus<List<CountriesEntities>>?>
    @Mock
    lateinit var currencyConverterObserver: Observer<DataStatus<CurrencyConverterEntity>?>

    @Mock
    lateinit var currencyListWithDateObserver: Observer<DataStatus<CurrenciesDate>?>

    @Captor
    lateinit var currenciesCaptor: ArgumentCaptor<DataStatus<List<CurrencyEntities>>?>
    @Captor
    lateinit var countriesCaptor: ArgumentCaptor<DataStatus<List<CountriesEntities>>?>
    @Captor
    lateinit var currencyConverterCaptor: ArgumentCaptor<DataStatus<CurrencyConverterEntity>?>
    @Captor
    lateinit var currencyListWithDateCaptor: ArgumentCaptor<DataStatus<CurrenciesDate>?>

    private var currencyListUseCase = mock(GetCurrencyUseCase::class.java)
    private var countryListUseCase =mock(GetCountriesUseCase::class.java)
    private var currencyConverterUseCase = mock(GetCurrencyConverterUseCase::class.java)
    private var currencyListWithDateUseCase = mock(GetCurrenciesListWithDateUseCase::class.java)

    private lateinit var currencyViewModel : CurrencyViewModel
    @Before
    fun setup(){
        currencyViewModel = CurrencyViewModel(currencyListUseCase,countryListUseCase,currencyConverterUseCase,currencyListWithDateUseCase,Schedulers.trampoline(),Schedulers.trampoline())
    }
    @Test
    fun `getRemoteCurrency() then hit loading and success with data`() {

        // arrange
        val currencyEntity = CurrencyEntities("Albanian Lek", null, "ALL")
        val currencyEntityII = CurrencyEntities("East Caribbean Dollar", null, "XCD")
        val currencyList = ArrayList<CurrencyEntities>()
        currencyList.add(currencyEntity)
        currencyList.add(currencyEntityII)

        Mockito.`when`(currencyListUseCase.invoke()).thenReturn(
            Single.just(
                currencyList
            )
        )
        currencyViewModel.currencyLiveData.observeForever(currencyObserver)

        // act
        currencyViewModel.getCurrency()

        //assert
        Mockito.verify(currencyObserver, Mockito.times(2))
            .onChanged(currenciesCaptor.capture())
        val values = currenciesCaptor.allValues

        assertEquals(DataStatus.Status.LOADING, values[0]?.status)
        assertEquals(DataStatus.Status.SUCCESS, values[1]?.status)
        assertEquals(currencyList, values[1]?.data)
    }


    @Test
    fun `getRemoteCountries() then hit loading and success with data`() {

        // arrange
        val countryEntity =
            CountriesEntities("AFG", "AFN", "Afghan afghani", null, "AF", "Afghanistan")
        val countryEntityII =
            CountriesEntities(
                "AIA", "XCD",
                "East Caribbean dollar", null,
                "AI", "Anguilla"
            )
        val countryList = ArrayList<CountriesEntities>()
        countryList.add(countryEntity)
        countryList.add(countryEntityII)

        Mockito.`when`(countryListUseCase.invoke()).thenReturn(
            Single.just(
                countryList
            )
        )
        currencyViewModel.countriesLiveData.observeForever(countriesObserver)

        // act
        currencyViewModel.getCountries()

        //assert
        Mockito.verify(countriesObserver, Mockito.times(2))
            .onChanged(countriesCaptor.capture())
        val values = countriesCaptor.allValues

        assertEquals(DataStatus.Status.LOADING, values[0]?.status)
        assertEquals(DataStatus.Status.SUCCESS, values[1]?.status)
        assertEquals(countryList, values[1]?.data)
    }



    @Test
    fun `getCurrencyConverter() then hit loading and success with data`() {

        // arrange
        val baseCurrency = "HKD_IDR"
        val secondCurrency = "IDR_HKD"

        val currencyConverterEntity =
            CurrencyConverterEntity("1842.989039", "0.000543")

        Mockito.`when`(currencyConverterUseCase.invoke(baseCurrency, secondCurrency)).thenReturn(
            Single.just(
                currencyConverterEntity
            )
        )
        currencyViewModel.currencyConverterLiveData.observeForever(currencyConverterObserver)

        // act
        currencyViewModel.getCurrencyConverter(baseCurrency, secondCurrency)

        //assert
        Mockito.verify(currencyConverterObserver, Mockito.times(2))
            .onChanged(currencyConverterCaptor.capture())
        val values = currencyConverterCaptor.allValues

        assertEquals(DataStatus.Status.LOADING, values[0]?.status)
        assertEquals(DataStatus.Status.SUCCESS, values[1]?.status)
        assertEquals(currencyConverterEntity, values[1]?.data)
    }
    @Test
    fun `getCurrencyListWithDate() then hit loading and success with data`() {

        // arrange
        val baseCurrency = "USD_PHP"
        val secondCurrency = "PHP_USD"
        val lastDate = "2022-4-28"
        val currentDate = "2022-5-4"

        val currencyDate =
            CurrenciesDate(
                BaseCurrenciesEntity(
                    arrayListOf(
                        "2022-4-28",
                        "2022-4-29",
                        "2022-4-30",
                        "2022-5-1",
                        "2022-5-2",
                        "2022-5-3",
                        "2022-5-4"
                    ),
                    arrayListOf(
                        "52.590194",
                        "52.380504",
                        "52.380504",
                        "52.340096",
                        "52.760211",
                        "52.809952",
                        "52.809952",
                        "52.450502"
                    )
                ),
                SecondCurrenciesEntity(
                    arrayListOf(
                        "2022-04-28",
                        "2022-04-29",
                        "2022-04-30",
                        "2022-05-01",
                        "2022-05-02",
                        "2022-05-03",
                        "2022-05-04"
                    ),
                    arrayListOf(
                        "0.019015",
                        "0.019091",
                        "0.019091",
                        "0.019106",
                        "0.018954",
                        "0.018936",
                        "0.019066"
                    )
                )
            )
        Mockito.`when`(currencyListWithDateUseCase.invoke(baseCurrency, secondCurrency,lastDate, currentDate)).thenReturn(
            Single.just(
                currencyDate
            )
        )
        currencyViewModel.currencyListLiveData.observeForever(currencyListWithDateObserver)

        // act
        currencyViewModel.getCurrencyListWithDate(baseCurrency, secondCurrency,lastDate, currentDate)

        //assert
        Mockito.verify(currencyListWithDateObserver, Mockito.times(2))
            .onChanged(currencyListWithDateCaptor.capture())
        val values = currencyListWithDateCaptor.allValues

        assertEquals(DataStatus.Status.LOADING, values[0]?.status)
        assertEquals(DataStatus.Status.SUCCESS, values[1]?.status)
        assertEquals(currencyDate, values[1]?.data)
    }

}