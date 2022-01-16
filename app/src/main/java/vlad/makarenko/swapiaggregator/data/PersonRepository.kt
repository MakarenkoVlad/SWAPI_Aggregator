package vlad.makarenko.swapiaggregator.data

import androidx.paging.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import vlad.makarenko.swapiaggregator.data.local.PersonDao
import vlad.makarenko.swapiaggregator.data.model.Person
import vlad.makarenko.swapiaggregator.data.remote.PersonRemoteMediator
import vlad.makarenko.swapiaggregator.data.remote.SWAPIService
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Singleton
class PersonRepository @Inject constructor(
    private val personDao: PersonDao,
    private val swapiService: SWAPIService,
    private val networkConnectionManager: NetworkConnectionManager
) : BaseRepository() {

    fun getPeople(query: String?) =
        networkConnectionManager.isConnected.flatMapLatest { isConnected ->
            Pager(
                config = PagingConfig(pageSize = 10, enablePlaceholders = false),
                remoteMediator = PersonRemoteMediator(personDao, swapiService)
            ) {
                personDao.getByQuery()
            }.flow.map { pagingData ->
                pagingData.filter { person -> person.name.contains(query ?: "", ignoreCase = true) }
            }
        }

    suspend fun updatePerson(person: Person) {
        personDao.update(person)
    }

    fun getFavouritePeople() = personDao.getAllFavourites()

    fun getPersonById(id: Int) = personDao.getById(id)
}
