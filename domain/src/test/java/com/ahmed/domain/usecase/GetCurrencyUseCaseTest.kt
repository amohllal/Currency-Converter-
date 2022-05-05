package com.ahmed.domain.usecase

import com.ahmed.domain.entities.*
import com.ahmed.domain.repository.CurrencyRepository
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GetCurrencyUseCaseTest {
    lateinit var currencyRepo: CurrencyRepository

    @Before
    fun setup() {
        currencyRepo = mock(CurrencyRepository::class.java)
    }

    @Test
    fun `getCurrenciesList() when internet connected then return currenciesList`() {
        val currencyEntity = CurrencyEntities("Albanian Lek", null, "ALL")
        val currencyEntityII = CurrencyEntities("East Caribbean Dollar", null, "XCD")
        val currencyList = ArrayList<CurrencyEntities>()
        currencyList.add(currencyEntity)
        currencyList.add(currencyEntityII)

        `when`(currencyRepo.getAllCurrency()).thenReturn(Single.just(currencyList))

        val result = GetCurrencyUseCase(currencyRepo).invoke().blockingGet()

        assertEquals(result, currencyList)
    }
}