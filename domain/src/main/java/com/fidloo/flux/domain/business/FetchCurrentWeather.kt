package com.fidloo.flux.domain.business

import com.fidloo.flux.domain.base.FlowUseCase
import com.fidloo.flux.domain.base.Result
import com.fidloo.flux.domain.di.MainDispatcher
import com.fidloo.flux.domain.model.WeatherFacts
import com.fidloo.flux.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchCurrentWeather @Inject constructor(
    private val repository: WeatherRepository,
    @MainDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, WeatherFacts>(dispatcher) {

    override fun execute(parameters: Unit): Flow<Result<WeatherFacts>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(repository.fetchCurrentWeather()))
        }
    }
}
