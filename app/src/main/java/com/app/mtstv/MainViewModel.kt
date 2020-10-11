package com.app.mtstv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.domain.FilmDomain
import com.app.domain.FilmsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MainViewModel(filmsUseCase: FilmsUseCase) : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private sealed class Filter {
        companion object {
            const val YEAR = 2020
        }
        object All : Filter()
        class New(val year: Int = YEAR) : Filter()
    }

    private val filterTypeChannel = BroadcastChannel<Filter>(Channel.CONFLATED)

    val filmsLiveData by lazy {
        val result = filterTypeChannel.asFlow().flatMapLatest { filer ->
            flow {
                emitAll(filmsUseCase.fetchFilms().catch { cause: Throwable ->
                    Log.e(TAG, "Something went wrong", cause)
                }.map { films ->
                    when(filer) {
                        is Filter.All -> {
                            films
                        }
                        is Filter.New -> {
                            films.filter {
                                it.year == filer.year
                            }
                        }
                    }
                })
            }
        }.asLiveData()
        filterTypeChannel.offer(Filter.All)
        result
    }

    fun onSwitchClicked(switchState: Boolean) {
        if(!switchState) {
            filterTypeChannel.offer(Filter.All)
        } else {
            filterTypeChannel.offer(Filter.New())
        }
    }
}