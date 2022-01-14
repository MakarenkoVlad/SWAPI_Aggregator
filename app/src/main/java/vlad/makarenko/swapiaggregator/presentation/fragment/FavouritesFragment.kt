package vlad.makarenko.swapiaggregator.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import vlad.makarenko.swapiaggregator.R
import vlad.makarenko.swapiaggregator.databinding.FragmentFavouritesBinding
import vlad.makarenko.swapiaggregator.presentation.adapter.PeopleAdapter
import vlad.makarenko.swapiaggregator.presentation.viewmodel.FavouritesViewModel
import vlad.makarenko.swapiaggregator.utils.autoCleanedVariable

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private val binding by viewBinding(FragmentFavouritesBinding::bind)

    private val viewModel: FavouritesViewModel by viewModels()

    private val adapter by autoCleanedVariable { PeopleAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        initObservers()
    }

    private fun initViews() {
        binding.rvFavourites.adapter = adapter
        adapter.setOnItemAddToFavouriteClickListener { person, _ -> viewModel.updatePerson(person) }
        adapter.setOnItemClickListener { person, _ ->
            person?.let {
                findNavController().navigate(
                    R.id.detailsFragment,
                    DetailsFragmentArgs(it.id).toBundle()
                )
            }
        }
    }

    private fun initObservers() {
        viewModel.people.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
