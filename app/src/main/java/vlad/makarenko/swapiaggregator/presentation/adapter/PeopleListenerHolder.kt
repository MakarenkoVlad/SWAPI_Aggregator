package vlad.makarenko.swapiaggregator.presentation.adapter

import vlad.makarenko.swapiaggregator.data.model.Person

interface PeopleListenerHolder {
    var onClick: ((Person?, Int) -> Unit)?
    var onAddToFavouriteClick: ((Person?, Int) -> Unit)?
}