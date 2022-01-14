package vlad.makarenko.swapiaggregator.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import vlad.makarenko.swapiaggregator.R
import vlad.makarenko.swapiaggregator.databinding.FragmentSearchBinding
import vlad.makarenko.swapiaggregator.presentation.adapter.PeoplePagingAdapter
import vlad.makarenko.swapiaggregator.presentation.viewmodel.SearchViewModel
import vlad.makarenko.swapiaggregator.utils.autoCleanedVariable
import vlad.makarenko.swapiaggregator.utils.showSnackBar
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel: SearchViewModel by viewModels()

    private val adapter by autoCleanedVariable { PeoplePagingAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        initObservers()

        initListeners()
    }

    private fun initViews() {
        binding.rvSearch.adapter = adapter

        binding.svMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText)
                return true
            }
        })
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.people.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initListeners() {
        adapter.setOnItemAddToFavouriteClickListener { person, _ ->
            viewModel.updatePerson(
                person
            )
        }
        adapter.setOnItemClickListener { person, _ ->
            person?.let {
                findNavController().navigate(
                    R.id.detailsFragment,
                    DetailsFragmentArgs(it.id).toBundle()
                )
            }
        }
        adapter.addLoadStateListener { loadStates ->
            when (val append = loadStates.append) {
                is LoadState.Error -> handleException(append.error)
                else -> Unit
            }
            when (val refresh = loadStates.refresh) {
                is LoadState.Error -> handleException(refresh.error)
                else -> Unit
            }
        }
    }

    private fun handleException(t: Throwable) {
        when (t) {
            is HttpException -> binding.root.showSnackBar(R.string.unexcpected_server_troubles)
            is UnknownHostException -> binding.root.showSnackBar(R.string.turn_on_interner)
            else -> binding.root.showSnackBar(t.toString())
        }
    }
}
