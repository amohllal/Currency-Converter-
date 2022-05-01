package com.ahmed.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Country")
data class CountriesLocalEntity(
    @ColumnInfo(name = "alpha3")
    val alpha3: String,
    @ColumnInfo(name = "currencyId")
    val currencyId: String,
    @ColumnInfo(name = "currencyName")
    val currencyName: String,
    @ColumnInfo(name = "currencySymbol")
    val currencySymbol: String? = null,
    @ColumnInfo(name = "CountryId")
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String
)