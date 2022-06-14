package com.example.finalspace.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.dsl.module

val episodeModule = module {
    single<EpisodeDataSource> { EpisodeDataSourceImpl(get()) }
}

@Serializable
data class SerializableEpisode(
    val id: Long,
    val name: String?,
    @SerialName("air_date") val airDate: String?,
    val director: String?,
    val writer: String?,
    @SerialName("img_url") val imgUrl: String?
)

interface EpisodeDataSource {
    suspend fun getAllChapters(): SerialResponse<List<SerializableEpisode>>
}

class EpisodeDataSourceImpl(private val httpClient: HttpClient) : EpisodeDataSource {

    override suspend fun getAllChapters(): SerialResponse<List<SerializableEpisode>> {
        return try {
            SerialResponse.Success(
                httpClient.get { url("https://finalspaceapi.com/api/v0/episode/") }
            )
        } catch (e: ClientRequestException) {
            SerialResponse.Error(data = null, message = e.message)
        } catch (e: IOException) {
            SerialResponse.Error(data = null, message = e.message.orEmpty())
        }
    }
}
