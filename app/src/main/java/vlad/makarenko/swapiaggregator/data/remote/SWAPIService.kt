package vlad.makarenko.swapiaggregator.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vlad.makarenko.swapiaggregator.data.model.FilmResponse
import vlad.makarenko.swapiaggregator.data.model.PeopleResponse
import vlad.makarenko.swapiaggregator.data.model.PersonResponse

interface SWAPIService {

    @GET("people")
    suspend fun getPeople(@Query("page") pageId: Int, @Query("search") query: String?): PeopleResponse

    @GET("films/{id}")
    suspend fun getFilmById(@Path("id") id: Int): FilmResponse
}
