package vlad.makarenko.swapiaggregator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class Person(
    @PrimaryKey
    val id: Int,
    val birthYear: String,
    val created: String,
    val eyeColor: String,
    val films: List<Int>,
    val gender: String,
    val hairColor: String,
    val height: String,
    val mass: String,
    val name: String,
    val skinColor: String,
    val isFavourite: Boolean,
)
