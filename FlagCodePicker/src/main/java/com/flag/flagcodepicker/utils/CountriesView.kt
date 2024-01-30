package com.flag.flagcodepicker.utils

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.flag.flagcodepicker.R
import com.flag.flagcodepicker.ui.componants.paddingHigher
import com.flag.flagcodepicker.ui.componants.paddingNormal
import com.flag.flagcodepicker.ui.componants.paddingSmall
import com.flag.flagcodepicker.ui.componants.searchBar
import java.util.Locale

@Composable
private fun CountryItem(
    country: Country,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(paddingNormal())
            .clickable(onClick = onItemClick)
    )
    {
        Image(
            painter = painterResource(id = country.flag),
            contentDescription = stringResource(id = country.nameID),
            modifier = Modifier
                .size(30.dp)
                .padding(end = paddingNormal()),
            alignment = Alignment.Center
        )
        Text(
            text = stringResource(id = country.nameID),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.padding(paddingSmall()))
        Text(
            text = country.code,
            textAlign = TextAlign.Start
        )
    }
    Divider()


}

@Composable
fun CountryCodePicker(
    modifier: Modifier = Modifier,
    countries: List<Country> = countries(),
    onCountrySelected: (Country) -> Unit,
    displaySearch: Boolean = true,
    searchModifier: Modifier = Modifier,
    context: Context
) {
    val filteredCountries = remember { mutableStateListOf<Country>() }

    Column(
        modifier.fillMaxSize()
            .padding(paddingHigher())
    ) {
        if (displaySearch) {
            searchBar(onValueChange = { query ->
                filterCountries(countries, query, context) { filteredList ->
                    filteredCountries.clear()
                    filteredCountries.addAll(filteredList)
                }
            }, modifier = searchModifier.fillMaxWidth())
        }
        LazyColumn(content = {
            items(filteredCountries.size) { index ->
                val countryItem = filteredCountries[index]
                CountryItem(country = countryItem) {
                    onCountrySelected(countryItem)
                }
            }
        })
    }
    // Initialize the filteredCountries list with the full list of countries
    LaunchedEffect(countries) {
        filteredCountries.addAll(countries)
    }
}
@Composable
fun codeCountryView(
    modifier: Modifier = Modifier,
    visibleFullCountry: Boolean = false
) {
    val context = LocalContext.current
    val isCountryCodePickerVisible = remember {
        mutableStateOf(false)
    }
    val selectedCountry= remember {
        mutableStateOf<Country?>(null)
    }
    if (isCountryCodePickerVisible.value) {
        CountryCodePicker(
            onCountrySelected = {
                Log.d("CountryCodePicker:", "country:${it.nameID}")
                isCountryCodePickerVisible.value = false
                selectedCountry.value = it
            },
            context = context
        )
    } else {
        Row(modifier = modifier
            .padding(paddingNormal())
            .clickable {
                isCountryCodePickerVisible.value = true
            }) {
            SelectedLocaleCountryInfo(selectedCountry=selectedCountry.value, visibleFullCountry = visibleFullCountry)
        }
    }
}

@Composable
fun SelectedLocaleCountryInfo(countries: List<Country> = countries(), selectedCountry: Country?=null, visibleFullCountry:Boolean=true
) {
    val locale = Locale.getDefault()
    val localeCountryCode = locale.country
    Log.d("main_locale_",localeCountryCode+":iso,"+locale.isO3Country)

    val country = selectedCountry ?: countries.find { it.codeText == localeCountryCode }
    val countryName = if (country != null) stringResource(country.nameID) else ""
    val codeText = country?.codeText ?: ""
    val code = country?.code ?: ""
    val flagResourceId = country?.flag ?: R.drawable.af


    Row(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(flagResourceId),
            contentDescription = "Flag",
            modifier = Modifier.padding(end = paddingNormal()).size(24.dp)
        )
        val countryText= if(visibleFullCountry) "$countryName ($code) " else "$codeText ($code) "
        Text(text =  countryText)
    }
}