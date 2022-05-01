package com.ahmed.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Currency")
data class CurrencyLocalEntity(
    @ColumnInfo(name = "currencyName")
    val currencyName: String,
    @ColumnInfo(name = "currencySymbol")
    val currencySymbol: String? = null,
    @ColumnInfo(name = "currencyId")
    @PrimaryKey
    val id: String


)
