package com.example.finalspace.data

import com.example.finalspace.data.local.EpisodeDao
import com.example.finalspace.data.remote.EpisodeDataSource
import com.example.finalspace.data.remote.SerialResponse
import com.example.finalspace.domain.Contractors
import entities.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.koin.dsl.module

val episodeRepositoryModule = module {
    single { EpisodeRepository(get(), get()) }
}

class EpisodeRepository(
    private val episodeDataSource: EpisodeDataSource,
    private val episodeDao: EpisodeDao
) : Contractors {
    override fun getLatestMovies(): Flow<NResponse<List<Episode>>> =
        networkBoundResource(
            { episodeDao.loadAllEpisodes() },
            { episodeDataSource.getAllChapters() },
            { response ->
                if (response is SerialResponse.Success) {
                    episodeDao.saveEpisodes(
                        response.data.map {
                            Episode(
                                it.id,
                                it.name,
                                it.airDate,
                                it.director,
                                it.writer,
                                it.imgUrl,
                            )
                        }
                    )
                } else {
                    episodeDao.deleteAllEpisodes()
                }
            }
        ).flowOn(Dispatchers.Default)
}