package com.example.finalspace.android.vm

import androidx.lifecycle.viewModelScope
import com.example.finalspace.android.BaseState
import com.example.finalspace.android.EpisodeState
import com.example.finalspace.data.NResponse
import com.example.finalspace.domain.EpisodeUseCase
import entities.Episode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}

class HomeViewModel(private val episodeUseCase: EpisodeUseCase) :
    BaseViewModel<EpisodeState.Event, EpisodeState.State, EpisodeState.Effect>() {
    init {
        viewModelScope.launch {
            val l: Flow<NResponse<List<Episode>>> = episodeUseCase.getAllEpisodes()
            l.collect { result ->

                val state: EpisodeState.State = when (result) {
                    is NResponse.Success -> {
                        EpisodeState.State.ShowAllEpisodes(
                            BaseState.Success(data = result.data)
                        )
                    }
                    is NResponse.Loading -> {
                        EpisodeState.State.ShowAllEpisodes(
                            BaseState.Loading(
                                data = result.data
                            )
                        )
                    }
                    is NResponse.Error -> {
                        EpisodeState.State.ShowAllEpisodes(
                            BaseState.Error
                        )
                    }
                }
                setState {
                    state
                }
            }
        }
    }

    override fun createInitialState(): EpisodeState.State = EpisodeState.State.ShowAllEpisodes(
        BaseState.Idle
    )

    override fun handleEvent(event: EpisodeState.Event) {
        when (event) {
            is EpisodeState.Event.OnMovieSelected -> {
                setEffect {
                    EpisodeState.Effect.GoToDetailMovie(event.id)
                }
            }
        }
    }
}
