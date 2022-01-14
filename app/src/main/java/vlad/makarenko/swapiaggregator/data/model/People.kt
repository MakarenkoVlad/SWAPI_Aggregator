package vlad.makarenko.swapiaggregator.data.model

import com.squareup.moshi.Json

data class People(
    @Json(name = "count")
    val count: Int,
    @Json(name = "next")
    val next: Int?,
    @Json(name = "previous")
    val previous: Int?,
    @Json(name = "results")
    val results: List<Person>
)
