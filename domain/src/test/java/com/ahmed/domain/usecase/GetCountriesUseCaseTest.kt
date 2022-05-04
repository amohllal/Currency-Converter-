package com.ahmed.domain.usecase

import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.repository.CurrencyRepository
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetCountriesUseCaseTest {
    lateinit var currencyRepo: CurrencyRepository

    @Before
    fun setup() {
        currencyRepo = Mockito.mock(CurrencyRepository::class.java)
    }

    @Test
    fun `getCountryList() when internet connected then return countryList`() {
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

        Mockito.`when`(currencyRepo.getAllCountries()).thenReturn(Single.just(countryList))

        val result = GetCountriesUseCase(currencyRepo).invoke()?.blockingGet()

        assertEquals(result, countryList)
    }

    @Test
    fun `getCountryList() when internet disconnected then return countryList from localStorage`() {
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

        Mockito.`when`(currencyRepo.getCountryListFromLocalStorage())
            .thenReturn(Single.just(countryList))

        val result = GetCountriesUseCase(currencyRepo).getCountryFromDatabase()?.blockingGet()

        assertEquals(result, countryList)
    }

}