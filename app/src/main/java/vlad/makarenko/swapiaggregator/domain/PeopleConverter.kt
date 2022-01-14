package vlad.makarenko.swapiaggregator.domain

import vlad.makarenko.swapiaggregator.data.model.People
import vlad.makarenko.swapiaggregator.data.model.PeopleResponse

object PeopleConverter {

    fun fromPeopleResponse(peopleResponse: PeopleResponse) = with(peopleResponse) {
        People(
            count = count,
            next = UrlToIdConverter.getIdFromPageOrNull(next),
            previous = UrlToIdConverter.getIdFromPageOrNull(previous),
            results = results.map(PersonConverter::fromPersonResponse)
        )
    }
}
