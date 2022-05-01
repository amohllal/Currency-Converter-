package com.ahmed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmed.data.model.local.CountriesLocalEntity
import com.ahmed.data.model.local.CurrencyLocalEntity

@Database(entities = [CurrencyLocalEntity::class,CountriesLocalEntity::class], version = 2, exportSchema = false)
abstract class CurrencyDataBase : RoomDatabase() {
    abstract fun getCurrencyDao(): CurrencyDAO
    abstract fun getCountryDao(): CountriesDAO

}