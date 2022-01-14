package vlad.makarenko.swapiaggregator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vlad.makarenko.swapiaggregator.data.local.typeconverter.IntListTypeConverter
import vlad.makarenko.swapiaggregator.data.model.Film
import vlad.makarenko.swapiaggregator.data.model.PageKey
import vlad.makarenko.swapiaggregator.data.model.Person

@Database(entities = [Person::class, Film::class, PageKey::class], version = 1)
@TypeConverters(IntListTypeConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun personDao(): PersonDao

    abstract fun filmDao(): FilmDao

    abstract fun pageKeyDao(): PageKeyDao
}
