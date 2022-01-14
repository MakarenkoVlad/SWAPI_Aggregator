package vlad.makarenko.swapiaggregator.data.local.typeconverter

import androidx.room.TypeConverter

class IntListTypeConverter {

    @TypeConverter
    fun fromString(string: String) = buildList {
        string.split(";").dropLast(1).forEach { int ->
            add(int.toInt())
        }
    }

    @TypeConverter
    fun fromList(ints: List<Int>) = buildString {
        ints.forEach { int ->
            append("$int;")
        }
    }
}
