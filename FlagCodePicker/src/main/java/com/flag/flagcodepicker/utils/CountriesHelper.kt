package com.flag.flagcodepicker.utils

import android.content.Context

fun filterCountries(
    countries: List<Country>,
    query: String,
    context: Context,
    onFiltered: (List<Country>) -> Unit
) {
    if (query.isEmpty()) {
        onFiltered(countries)
    } else {
        val filteredList = countries.filter { country ->
            val countryName = context.getString(country.nameID)
            country.code.contains(query, ignoreCase = true) ||
                    countryName.contains(query, ignoreCase = true) ||
                    country.code == query
        }
        onFiltered(filteredList)
    }
}
