package com.ahmed.domain.usecase

import android.annotation.SuppressLint
import com.ahmed.domain.entities.BaseCurrenciesEntity
import com.ahmed.domain.entities.CurrenciesDate
import com.ahmed.domain.entities.CurrencyConverterEntity
import com.ahmed.domain.entities.SecondCurrenciesEntity
import com.ahmed.domain.repository.CurrencyRepository
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.net.UnknownHostException

class GetCurrencyWithDateUseCaseTest {
    lateinit var currencyRepo: CurrencyRepository

    @Before
    fun setup() {
        currencyRepo = Mockito.mock(CurrencyRepository::class.java)
    }

    @Test
    fun `getCurrencyListWithDate() when internet connected,base currency,second currency, end date and current date not null then return currencyList value`() {

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
        Mockito.`when`(
            currencyRepo.getCurrencyListWithDate(
                baseCurrency,
                secondCurrency,
                lastDate,
                currentDate
            )
        )
            .thenReturn(Single.just(currencyDate))

        val result = GetCurrenciesListWithDateUseCase(currencyRepo).invoke(
            baseCurrency, secondCurrency, lastDate, currentDate
        )?.blockingGet()

        assertEquals(result, currencyDate)
    }

    @SuppressLint("CheckResult")
    @Test(expected = UnknownHostException::class)
    fun `getCurrencyListWithDate() when internet disconnected,base currency,second currency, end date and current date not null then it should throw UnknownHostException`() {
        val baseCurrency = "USD_PHP"
        val secondCurrency = "PHP_USD"
        val lastDate = "2022-4-28"
        val currentDate = "2022-5-4"

        val currencyRepository = object : CurrencyRepository {
            override fun getCurrencyListWithDate(
                baseCurrency: String,
                secondCurrency: String,
                lastDate: String,
                currentDate: String
            ): Single<CurrenciesDate>? {
                throw UnknownHostException()
            }
        }

        GetCurrenciesListWithDateUseCase(currencyRepository).invoke(
            baseCurrency, secondCurrency, lastDate, currentDate
        )?.blockingGet()

    }
}