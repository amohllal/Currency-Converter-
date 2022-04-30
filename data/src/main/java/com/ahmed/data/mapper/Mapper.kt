package com.ahmed.data.mapper

import com.ahmed.data.model.Countries
import com.ahmed.data.model.CountriesModel
import com.ahmed.data.model.Currencies
import com.ahmed.data.model.CurrencyModel
import com.ahmed.domain.entities.CountriesEntities
import com.ahmed.domain.entities.CurrencyEntities

fun CurrencyModel.mapToList() = this.results.toList().map { hmap ->
    Currencies(
        currencyName = hmap.second.currencyName,
        currencySymbol = hmap.second.currencySymbol, id = hmap.second.id
    )
}


fun List<Currencies>.mapToDomain() = this.map {
    CurrencyEntities(currencyName = it.currencyName, currencySymbol = it.currencySymbol, id = it.id)
}

fun CountriesModel.mapToList() = this.results.toList().map { hmap ->
    Countries(
        currencyName = hmap.second.currencyName,
        currencySymbol = hmap.second.currencySymbol,
        id = hmap.second.id,
        alpha3 = hmap.second.alpha3,
        currencyId = hmap.second.currencyId,
        name = hmap.second.name
    )
}


fun List<Countries>.mapToCountriesDomain() = this.map {
    CountriesEntities(
        currencyName = it.currencyName,
        currencySymbol = it.currencySymbol,
        id = it.id,
        alpha3 = it.alpha3,
        currencyId = it.currencyId,
        name = it.name
    )
}
