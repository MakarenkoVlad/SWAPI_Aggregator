package vlad.makarenko.swapiaggregator.data.remote

import androidx.paging.* // ktlint-disable no-wildcard-imports
import timber.log.Timber
import vlad.makarenko.swapiaggregator.data.local.PersonDao
import vlad.makarenko.swapiaggregator.data.model.Person
import vlad.makarenko.swapiaggregator.domain.PeopleConverter

@ExperimentalPagingApi
class PersonRemoteMediator(
    private val personDao: PersonDao,
    private val swapiService: SWAPIService
) : RemoteMediator<Int, Person>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Person>): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH, LoadType.APPEND -> {
                    personDao.rowsCount() / 10 + 1
                }
                LoadType.PREPEND -> return MediatorResult.Success(true)
            }

            val peopleResponse =
                swapiService.getPeople(loadKey)
            val people = PeopleConverter.fromPeopleResponse(peopleResponse)
            personDao.insert(people.results)

            MediatorResult.Success(endOfPaginationReached = people.next == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}
