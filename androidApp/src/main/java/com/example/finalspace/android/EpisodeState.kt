package com.example.finalspace.android

import entities.Episode

interface EpisodeState {
    sealed class Event : UiEvent {
        data class OnMovieSelected(val id: Int) : Event()
    }

    sealed class State: UiState {
        data class ShowAllEpisodes(val episodes: BaseState<List<Episode>>) : State()
    }

    sealed class Effect: UiEffect {
        data class GoToDetailMovie(val id: Int): Effect()
    }
}