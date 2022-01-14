package vlad.makarenko.swapiaggregator.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import vlad.makarenko.swapiaggregator.data.model.Person

@Dao
interface PersonDao : BaseDao<Person> {

    @Query("SELECT * FROM persons WHERE isFavourite = 1")
    fun getAllFavourites(): Flow<List<Person>>

    @Query("SELECT * FROM persons WHERE name LIKE '%' || :query || '%'")
    fun getByQuery(query: String): PagingSource<Int, Person>

    @Query("SELECT * FROM persons WHERE :id = id")
    fun getById(id: Int): Flow<Person>
}
