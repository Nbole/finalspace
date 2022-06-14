package com.example.finalspace.domain

import com.example.finalspace.data.EpisodeRepository
import com.example.finalspace.data.NResponse
import entities.Episode
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

val useCaseModule = module {
    single { EpisodeUseCase() }
}

interface UseCaseContract {
    fun getAllEpisodes(): Flow<NResponse<List<Episode>>>
}

class EpisodeUseCase : UseCaseContract, KoinComponent {
    private val movieRepository: EpisodeRepository by inject()

    override fun getAllEpisodes(): Flow<NResponse<List<Episode>>> =
        movieRepository.getLatestMovies()
}