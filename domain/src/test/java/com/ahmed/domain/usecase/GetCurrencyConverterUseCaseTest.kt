package com.ahmed.domain.usecase

import android.annotation.SuppressLint
import com.ahmed.domain.entities.CurrencyConverterEntity
import com.ahmed.domain.repository.CurrencyRepository
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.net.UnknownHostException

class GetCurrencyConverterUseCaseTest {
    lateinit var currencyRepo: CurrencyRepository

    @Before
    fun setup() {
        currencyRepo = mock(CurrencyRepository::class.java)
    }

    @Test
    fun `getCurrencyConverter() when internet connected,base currency and second currency not null then return currency value`() {
        //arrange
        val baseCurrency = "HKD_IDR"
        val secondCurrency = "IDR_HKD"

        //act
        val currencyConverterEntity =
            CurrencyConverterEntity("1842.989039", "0.000543")
        `when`(currencyRepo.getConverterCurrency(baseCurrency,secondCurrency ))
            .thenReturn(Single.just(currencyConverterEntity))
        val result =
            GetCurrencyConverterUseCase(currencyRepo).invoke(baseCurrency, secondCurrency)?.blockingGet()

        //assert
        assertEquals(result, currencyConverterEntity)
    }

    @SuppressLint("CheckResult")
    @Test(expected = UnknownHostException::class)
    fun `getCurrencyConverter() when internet disconnected,base currency and second currency not null it should throw UnknownHostException`() {
        val baseCurrency = "HKD_IDR"
        val secondCurrency = "IDR_HKD"

        val currencyRepository = object : CurrencyRepository {
            override fun getConverterCurrency(
                baseCurrency: String,
                secondCurrency: String
            ): Single<CurrencyConverterEntity> {
                throw UnknownHostException()
            }
        }


        GetCurrencyConverterUseCase(currencyRepository).invoke(baseCurrency, secondCurrency)?.blockingGet()


    }
}