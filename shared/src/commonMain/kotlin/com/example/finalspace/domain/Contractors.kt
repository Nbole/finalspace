package com.example.finalspace.domain

import com.example.finalspace.data.NResponse
import entities.Episode
import kotlinx.coroutines.flow.Flow

interface Contractors {
    fun getLatestMovies(): Flow<NResponse<List<Episode>>>
}
