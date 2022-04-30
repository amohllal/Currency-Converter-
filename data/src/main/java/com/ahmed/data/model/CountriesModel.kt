package com.ahmed.data.model

import com.google.gson.annotations.SerializedName

data class CountriesModel(
    @field:SerializedName("results")
    val results : Map<String, Countries>
)
data class Countries(
    val alpha3: String,
    val currencyId: String,
    val currencyName: String,
    val currencySymbol: String?= null,
    val id: String,
    val name: String

)
