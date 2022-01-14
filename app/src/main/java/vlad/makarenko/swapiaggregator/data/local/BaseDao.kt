package vlad.makarenko.swapiaggregator.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(models: List<T>)

    @Insert
    suspend fun insert(model: T)

    @Update
    suspend fun update(model: T)
}
