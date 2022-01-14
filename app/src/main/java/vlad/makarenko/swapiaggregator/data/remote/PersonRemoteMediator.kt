package vlad.makarenko.swapiaggregator.data.remote

import androidx.paging.* // ktlint-disable no-wildcard-imports
import androidx.room.withTransaction
import timber.log.Timber
import vlad.makarenko.swapiaggregator.data.local.Database
import vlad.makarenko.swapiaggregator.data.model.PageKey
import vlad.makarenko.swapiaggregator.data.model.Person
import vlad.makarenko.swapiaggregator.domain.PeopleConverter

@ExperimentalPagingApi
class PersonRemoteMediator(
    private val database: Database,
    private val swapiService: SWAPIService,
    private val query: String?
) : RemoteMediator<Int, Person>() {

    private val pageKeyDao = database.pageKeyDao()
    private val personDao = database.personDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Person>): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    if (state.isEmpty())
                        1
                    else
                        return MediatorResult.Success(true)
                }
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val person = state.lastItemOrNull()
                    if (person == null) {
                        1
                    } else {
                        val pageKey = database.withTransaction {
                            pageKeyDao.getPageKeyById(person.id)
                        }
                        if (pageKey == null) {
                            1
                        } else {
                            pageKey.nextPageId ?: return MediatorResult.Success(true)
                        }
                    }
                }
            }

            val peopleResponse =
                swapiService.getPeople(loadKey, query)
            val people = PeopleConverter.fromPeopleResponse(peopleResponse)

            database.withTransaction {
                pageKeyDao.insert(people.results.map { PageKey(it.id, people.next) })
                personDao.insert(people.results)
            }

            MediatorResult.Success(endOfPaginationReached = people.next == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}
