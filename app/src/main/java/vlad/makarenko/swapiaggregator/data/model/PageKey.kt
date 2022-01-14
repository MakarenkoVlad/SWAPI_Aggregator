package vlad.makarenko.swapiaggregator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "page_keys")
data class PageKey(
    @PrimaryKey
    val personId: Int,
    val nextPageId: Int?
)
