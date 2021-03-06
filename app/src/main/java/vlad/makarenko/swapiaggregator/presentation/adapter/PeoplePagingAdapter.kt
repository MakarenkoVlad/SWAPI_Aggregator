package vlad.makarenko.swapiaggregator.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import vlad.makarenko.swapiaggregator.data.model.Person

class PeoplePagingAdapter : PagingDataAdapter<Person, PersonViewHolder>(PersonDiffer) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PersonViewHolder.create(parent, listenerHolder)

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val model = getItem(position)
        if (model != null)
            holder.bind(model, position)
    }

    private val listenerHolder = object : PeopleListenerHolder {
        override var onClick: ((Person?, Int) -> Unit)? = null
        override var onAddToFavouriteClick: ((Person?, Int) -> Unit)? = null
    }

    fun setOnItemClickListener(block: (Person?, Int) -> Unit) {
        listenerHolder.onClick = block
    }

    fun setOnItemAddToFavouriteClickListener(block: (Person?, Int) -> Unit) {
        listenerHolder.onAddToFavouriteClick = block
    }
}
