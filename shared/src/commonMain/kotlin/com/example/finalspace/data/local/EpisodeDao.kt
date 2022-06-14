package com.example.finalspace.data.local

import com.example.finalspace.AppDatabase
import com.example.finalspace.DatabaseDriverFactory
import entities.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.koin.dsl.module

val episodeDao = module {
    single<EpisodeDao> { EpisodeDaoDaoImpl(get()) }
}

interface EpisodeDao {
    fun deleteAllEpisodes()
    suspend fun saveEpisodes(input: List<Episode>)
    fun loadAllEpisodes(): Flow<List<Episode>>
}

class EpisodeDaoDaoImpl(databaseDriverFactory: DatabaseDriverFactory) : EpisodeDao {
    private val database = AppDatabase.invoke(databaseDriverFactory.createDriver())
    private val queries = database.appDataBaseQueries

    override fun deleteAllEpisodes() { queries.deleteAllCharacters() }

    override suspend fun saveEpisodes(input: List<Episode>) {
        withContext(Dispatchers.Default) {
            queries.transaction {
                deleteAllEpisodes()
                input.map {
                    queries.saveEpisotes(
                        id = it.id,
                        name = it.name,
                        imgUrl = it.imgUrl,
                        airDate = it.airDate,
                        director = it.director,
                        writer = it.writer,
                    )
                }
            }
        }
    }


    override fun loadAllEpisodes(): Flow<List<Episode>> {
        return flow { emit(queries.loadAllEpisodes().executeAsList()) }
    }
}