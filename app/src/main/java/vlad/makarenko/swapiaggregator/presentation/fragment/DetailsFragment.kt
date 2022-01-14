package vlad.makarenko.swapiaggregator.presentation.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.HttpException
import vlad.makarenko.swapiaggregator.R
import vlad.makarenko.swapiaggregator.databinding.FragmentDetailsBinding
import vlad.makarenko.swapiaggregator.presentation.viewmodel.DetailsViewModel
import vlad.makarenko.swapiaggregator.utils.showSnackBar
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)

    private val viewModel: DetailsViewModel by viewModels()

    private val safeArgs: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.setId(safeArgs.id)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        initObservers()
    }

    private fun initViews() {
        binding.ivFilmsSwitch.setOnClickListener {
            viewModel.switchFilms()
        }
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivFavourite.setOnClickListener {
            viewModel.switchFavourite()
        }
    }

    private fun initObservers() {
        viewModel.person.observe(viewLifecycleOwner) { person ->
            binding.lvGeneral.adapter = ArrayAdapter(
                requireContext(), R.layout.item_text, R.id.tvText,
                listOf(
                    getString(R.string.name, person.name),
                    getString(R.string.gender, person.gender),
                    getString(R.string.birth_year, person.birthYear),
                    getString(R.string.height, person.height),
                    getString(R.string.mass, person.mass),
                    getString(R.string.skin_color, person.skinColor),
                    getString(R.string.hair_color, person.hairColor)
                )
            )
            binding.ivFavourite.imageTintList = ColorStateList.valueOf(
                if (person.isFavourite) requireContext().getColor(R.color.purple_700) else requireContext().getColor(
                    R.color.black
                )
            )
        }
        viewModel.films.observe(viewLifecycleOwner) { films ->
            binding.lvFilms.adapter = ArrayAdapter(
                requireContext(), R.layout.item_text, R.id.tvText,
                films.map { it.title }
            )
        }

        viewModel.isFilmsShown.observe(viewLifecycleOwner) {
            binding.lvFilms.isVisible = it
            binding.ivFilmsSwitch.setImageResource(
                if (it) {
                    R.drawable.ic_expand_more
                } else {
                    R.drawable.ic_expand_less
                }
            )
        }

        viewModel.isFilmsProgressBarVisible.observe(viewLifecycleOwner) {
            binding.pbFilms.isVisible = it
        }

        viewModel.error.observe(viewLifecycleOwner) {
            handleException(it)
        }
    }

    private fun handleException(t: Throwable) {
        when (t) {
            is HttpException -> binding.root.showSnackBar(R.string.film_cant_be_uploaded)
            is UnknownHostException -> binding.root.showSnackBar(R.string.turn_on_interner)
            else -> binding.root.showSnackBar(t.toString())
        }
    }
}
