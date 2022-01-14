package vlad.makarenko.swapiaggregator.data.local

import androidx.room.Dao
import androidx.room.Query
import vlad.makarenko.swapiaggregator.data.model.PageKey

@Dao
interface PageKeyDao : BaseDao<PageKey> {

    @Query("SELECT * FROM page_keys WHERE :id = personId LIMIT 1")
    suspend fun getPageKeyById(id: Int): PageKey?
}
