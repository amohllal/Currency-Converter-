package com.ahmed.domain.entities

import com.google.gson.annotations.SerializedName

data class CurrencyEntities(
	val currencyName: String,
	val currencySymbol: String? = null,
	val id: String,
)