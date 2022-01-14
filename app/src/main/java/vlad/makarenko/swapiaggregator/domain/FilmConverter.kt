package vlad.makarenko.swapiaggregator.domain

import vlad.makarenko.swapiaggregator.data.model.Film
import vlad.makarenko.swapiaggregator.data.model.FilmResponse

object FilmConverter {

    fun fromFilmResponse(filmResponse: FilmResponse) = with(filmResponse) {
        Film(
            id = UrlToIdConverter.getIdFromUrl(url),
            title = title
        )
    }
}
