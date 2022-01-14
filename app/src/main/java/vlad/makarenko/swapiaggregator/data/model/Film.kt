package vlad.makarenko.swapiaggregator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class Film(
    @PrimaryKey
    val id: Int,
    val title: String,
)
