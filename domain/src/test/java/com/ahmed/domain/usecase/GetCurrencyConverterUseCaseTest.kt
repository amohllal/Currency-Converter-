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
        val currencyConverterEntity =
            CurrencyConverterEntity("1842.989039", "0.000543")

        `when`(currencyRepo.getConverterCurrency("HKD_IDR", "IDR_HKD"))
            .thenReturn(Single.just(currencyConverterEntity))

        val result =
            GetCurrencyConverterUseCase(currencyRepo).invoke("HKD_IDR", "IDR_HKD")?.blockingGet()

        assertEquals(result, currencyConverterEntity)
    }

    @SuppressLint("CheckResult")
    @Test(expected = UnknownHostException::class)
    fun `getCurrencyConverter() when internet disconnected,base currency and second currency not null it should throw UnknownHostException`() {
        val currencyConverterEntity =
            CurrencyConverterEntity("1842.989039", "0.000543")


        val currencyRepository = object : CurrencyRepository {
            override fun getConverterCurrency(
                baseCurrency: String,
                secondCurrency: String
            ): Single<CurrencyConverterEntity> {
                throw UnknownHostException()
            }
        }


        GetCurrencyConverterUseCase(currencyRepository).invoke("HKD_IDR", "IDR_HKD")?.blockingGet()


    }
}