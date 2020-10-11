package com.app.data

import com.app.domain.FilmDomain
import com.app.domain.FilmsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

fun data(filmsApi: FilmsApi, appCache: AppCache): FilmsRepository = FilmsDataRepository(filmsApi, appCache)

internal class FilmsDataRepository(
    private val filmsApi: FilmsApi,
    private val appCache: AppCache
) : FilmsRepository {

    override fun getFilms(): Flow<List<FilmDomain>> {
        if(appCache.isExists()) {
            return flow {
                emit(
                    appCache.getFromCache().map {
                        FilmDomain(it.id ,it.poster, it.year)
                    }
                )
            }
        } else {
            return filmsApi.getAccounts().flowOn(Dispatchers.IO).map { list ->
                appCache.saveByCache(list)
                list
            }.flowOn(Dispatchers.Main).map { list ->
                list.map { FilmDomain(it.id, it.poster, it.year) }
            }
        }
    }
}