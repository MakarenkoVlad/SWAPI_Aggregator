package vlad.makarenko.swapiaggregator.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vlad.makarenko.swapiaggregator.R
import vlad.makarenko.swapiaggregator.data.model.Person
import vlad.makarenko.swapiaggregator.databinding.ItemCharacterBinding

class PersonViewHolder(
    private val binding: ItemCharacterBinding,
    private val listenerHolder: PeopleListenerHolder
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Person?, position: Int) {
        with(binding) {
            val context = binding.root.context
            tvTitle.text = model?.name ?: context.getString(R.string.placeholder_name)
            root.setOnClickListener { listenerHolder.onClick?.invoke(model, position) }
            setIsFavourite(model?.isFavourite)
            ivFavourite.setOnClickListener {
                listenerHolder.onAddToFavouriteClick?.invoke(
                    model?.copy(isFavourite = !model.isFavourite),
                    position
                )
            }
        }
    }

    private fun setIsFavourite(isFavourite: Boolean?) {
        binding.ivFavourite.imageTintList = ColorStateList.valueOf(
            when (isFavourite) {
                true -> R.color.purple_700
                false, null -> R.color.black
            }
        )
    }

    companion object {
        fun create(parent: ViewGroup, listenerHolder: PeopleListenerHolder) = PersonViewHolder(
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listenerHolder
        )
    }
}
