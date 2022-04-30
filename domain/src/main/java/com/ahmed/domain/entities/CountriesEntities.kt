package com.ahmed.domain.entities

data class CountriesEntities(
    val alpha3: String,
    val currencyId: String,
    val currencyName: String,
    val currencySymbol: String?= null,
    val id: String,
    val name: String

)
