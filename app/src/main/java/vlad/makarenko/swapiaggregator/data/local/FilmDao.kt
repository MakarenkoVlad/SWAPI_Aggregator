package vlad.makarenko.swapiaggregator.data.local

import androidx.room.Dao
import androidx.room.Query
import vlad.makarenko.swapiaggregator.data.model.Film

@Dao
interface FilmDao : BaseDao<Film> {

    @Query("SELECT * FROM films WHERE id = :id")
    suspend fun getById(id: Int): Film?

    @Query("SELECT * FROM films")
    suspend fun getAll(): List<Film>?

    @Query("SELECT * FROM films WHERE id IN (:ids)")
    suspend fun getById(ids: List<Int>): List<Film>?
}
