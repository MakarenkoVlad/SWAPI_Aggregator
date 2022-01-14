package vlad.makarenko.swapiaggregator.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import vlad.makarenko.swapiaggregator.data.local.Database
import vlad.makarenko.swapiaggregator.data.model.Person
import vlad.makarenko.swapiaggregator.data.remote.PersonRemoteMediator
import vlad.makarenko.swapiaggregator.data.remote.SWAPIService
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Singleton
class PersonRepository @Inject constructor(
    private val database: Database,
    private val swapiService: SWAPIService,
    private val networkConnectionManager: NetworkConnectionManager
) : BaseRepository() {

    private val personDao = database.personDao()

    fun getPeople(query: String?) =
        networkConnectionManager.isConnected.flatMapLatest { isConnected ->
            if (isConnected)
                Pager(
                    config = PagingConfig(pageSize = 10),
                    remoteMediator = PersonRemoteMediator(database, swapiService, query),
                    initialKey = 1
                ) {
                    database.personDao().getByQuery(query ?: "")
                }.flow
            else
                Pager(
                    config = PagingConfig(pageSize = 10),
                    initialKey = 1
                ) {
                    database.personDao().getByQuery(query ?: "")
                }.flow
        }

    suspend fun updatePerson(person: Person) {
        personDao.update(person)
    }

    fun getFavouritePeople() = personDao.getAllFavourites()

    fun getPersonById(id: Int) = personDao.getById(id)
}
