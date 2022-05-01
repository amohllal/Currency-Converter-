package com.ahmed.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmed.data.model.local.CurrencyLocalEntity
import io.reactivex.Single

@Dao
interface CurrencyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveListOfCurrency(list: List<CurrencyLocalEntity>)


    @Query("SELECT * FROM Currency")
    fun getCurrencyFromDatabase(): Single<List<CurrencyLocalEntity>>
}