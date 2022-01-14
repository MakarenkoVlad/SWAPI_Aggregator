package vlad.makarenko.swapiaggregator.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import vlad.makarenko.swapiaggregator.data.model.Person

class PeopleAdapter : ListAdapter<Person, PersonViewHolder>(PersonDiffer) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PersonViewHolder.create(parent, listenerHolder)

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position), position)
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
