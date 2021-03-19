package com.fidloo.flux.domain.business

import com.fidloo.flux.domain.base.FlowUseCase
import com.fidloo.flux.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.fidloo.flux.domain.base.Result
import com.fidloo.flux.domain.di.IoDispatcher
import com.fidloo.flux.domain.di.MainDispatcher
import com.fidloo.flux.domain.model.HourWeather
import kotlinx.coroutines.flow.flow

class FetchHourlyWeather @Inject constructor(
    private val repository: WeatherRepository,
    @MainDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<HourWeather>>(dispatcher) {

    override fun execute(parameters: Unit): Flow<Result<List<HourWeather>>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(repository.fetchHourlyWeather()))
        }
    }
}
