package com.example.finalspace.data

import com.example.finalspace.AppDatabase
import com.example.finalspace.DatabaseDriverFactory
import entities.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.koin.dsl.module

val characterDao = module {
    single<CharacterDao> { CharacterDaoDaoImpl(get()) }
}

interface CharacterDao {
    fun deleteAllCharacters()
    suspend fun saveCharacters(input: List<Character>)
    fun loadAllCharacters(): Flow<List<Character>>
}

class CharacterDaoDaoImpl(databaseDriverFactory: DatabaseDriverFactory) : CharacterDao {
    private val database = AppDatabase.invoke(databaseDriverFactory.createDriver())
    private val queries = database.appDataBaseQueries

    override fun deleteAllCharacters() {
        queries.deleteAllCharacters()
    }

    override suspend fun saveCharacters(input: List<Character>) {
        withContext(Dispatchers.Default) {
            queries.transaction {
                deleteAllCharacters()
                input.map {
                    queries.saveCharacters(
                        id = it.id,
                        name = it.name,
                        status = it.status,
                        species = it.species,
                        gender = it.gender,
                        hair = it.hair,
                        alias = it.alias,
                        imgUrl = it.imgUrl
                    )
                }
            }
        }
    }

    override fun loadAllCharacters(): Flow<List<Character>> {
        return flow {
            emit(queries.loadAllCharacters().executeAsList())
        }
    }
}