package vlad.makarenko.swapiaggregator.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.withContext
import vlad.makarenko.swapiaggregator.data.local.FilmDao
import vlad.makarenko.swapiaggregator.data.model.Data
import vlad.makarenko.swapiaggregator.data.model.Film
import vlad.makarenko.swapiaggregator.data.remote.SWAPIService
import vlad.makarenko.swapiaggregator.domain.FilmConverter
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class FilmRepository @Inject constructor(
    private val swapiService: SWAPIService,
    private val filmDao: FilmDao,
    private val networkConnectionManager: NetworkConnectionManager,
) : BaseRepository() {

    fun getFilmsByIds(ids: List<Int>) = networkConnectionManager.isConnected.flatMapLatest {
        buildDataFlow<List<Film>> {
            val filmsFromLocal = filmDao.getById(ids)
            if (filmsFromLocal.isNullOrEmpty()) {
                val films: List<Film>
                withContext(Dispatchers.IO) {
                    films = buildList {
                        ids.forEach {
                            add(async { swapiService.getFilmById(it) })
                        }
                    }.map { FilmConverter.fromFilmResponse(it.await()) }
                }
                emit(Data.Success(films))
                filmDao.insert(films)
            } else {
                emit(Data.Success(filmsFromLocal))
            }
        } 
    }
}
