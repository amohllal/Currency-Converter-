package com.ahmed.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmed.data.model.local.CountriesLocalEntity
import com.ahmed.data.model.local.CurrencyLocalEntity
import io.reactivex.Single

@Dao
interface CountriesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveListOfCountries(list: List<CountriesLocalEntity>)


    @Query("SELECT * FROM Country")
    fun getCountryFromDatabase(): Single<List<CountriesLocalEntity>>

}