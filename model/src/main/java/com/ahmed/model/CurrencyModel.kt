package com.ahmed.model

import com.google.gson.annotations.SerializedName

data class CurrencyModel(
	@field:SerializedName("results")
	val results : Map<String, Currencies>
)
data class Currencies(
	val currencyName: String,
	val currencySymbol: String? = null,
	val id: String,
)