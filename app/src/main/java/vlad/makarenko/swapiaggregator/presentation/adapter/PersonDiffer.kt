package vlad.makarenko.swapiaggregator.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import vlad.makarenko.swapiaggregator.data.model.Person

object PersonDiffer : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean = oldItem == newItem
}