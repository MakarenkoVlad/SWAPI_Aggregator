package vlad.makarenko.swapiaggregator.domain

import vlad.makarenko.swapiaggregator.data.model.Person
import vlad.makarenko.swapiaggregator.data.model.PersonResponse

object PersonConverter {

    fun fromPersonResponse(personResponse: PersonResponse) = with(personResponse) {
        Person(
            id = UrlToIdConverter.getIdFromUrl(url),
            birthYear = birthYear,
            created = created,
            eyeColor = eyeColor,
            films = films.map { UrlToIdConverter.getIdFromUrl(it) },
            gender = gender,
            hairColor = hairColor,
            height = height,
            mass = mass,
            name = name,
            skinColor = skinColor,
            isFavourite = false
        )
    }
}
