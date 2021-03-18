package com.fidloo.flux.data.di

import com.fidloo.flux.data.repository.WeatherRepositoryImpl
import com.fidloo.flux.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository
}
