package com.fidloo.flux.presentation.ui.home.hourly

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun HourlyWeatherFilters(
    selectedFilter: HourlyWeatherType,
    onFilterSelected: (HourlyWeatherType) -> Unit
) {
    val items = HourlyWeatherType.values()
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        items(items) { item ->
            HourlyWeatherFilter(item, item == selectedFilter, onFilterSelected)
        }
    }
}