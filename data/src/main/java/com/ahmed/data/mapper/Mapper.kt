package com.ahmed.data.mapper

import com.ahmed.data.model.Currencies
import com.ahmed.data.model.CurrencyModel
import com.ahmed.domain.entities.CurrencyEntities

fun CurrencyModel.mapToList() = this.results.toList().map {hmap->
    Currencies(currencyName = hmap.second.currencyName,
        currencySymbol = hmap.second.currencySymbol, id = hmap.second.id)
}


fun List<Currencies>.mapToDomain() = this.map {
    CurrencyEntities(currencyName = it.currencyName, currencySymbol = it.currencySymbol, id = it.id)
}
