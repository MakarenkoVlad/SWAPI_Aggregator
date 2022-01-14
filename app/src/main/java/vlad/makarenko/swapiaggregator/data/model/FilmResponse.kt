package vlad.makarenko.swapiaggregator.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmResponse(
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
)