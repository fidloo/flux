package com.fidloo.flux.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fidloo.flux.presentation.R
import com.fidloo.flux.presentation.ui.component.SectionHeader
import com.fidloo.flux.presentation.ui.theme.FluxTheme

@Composable
fun WeatherRadar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        SectionHeader(title = "Weather radar", subtitle = "Satellite images")
        Spacer(Modifier.height(8.dp))

        Box(modifier = Modifier
            .padding(horizontal = 24.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                elevation = FluxTheme.elevations.Card
            ) {
                Image(
                    painter = painterResource(R.drawable.weather_radar_lyon),
                    contentDescription = "Weather satellite images",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}