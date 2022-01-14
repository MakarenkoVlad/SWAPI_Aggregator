package vlad.makarenko.swapiaggregator.presentation.viewmodel

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import vlad.makarenko.swapiaggregator.data.FilmRepository
import vlad.makarenko.swapiaggregator.data.PersonRepository
import vlad.makarenko.swapiaggregator.data.model.Data
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val personRepository: PersonRepository,
    private val filmRepository: FilmRepository
) : ViewModel() {

    private val id = MutableStateFlow<Int?>(null)

    fun setId(id: Int) = this.id.tryEmit(id)

    val person = id.mapNotNull { it }.flatMapLatest { personId ->
        personRepository
            .getPersonById(personId)
            .onEach { filmsId.tryEmit(it.films) }
    }.asLiveData(Dispatchers.IO)

    private val filmsId = MutableStateFlow<List<Int>>(emptyList())

    val films = filmsId.flatMapLatest { ints ->
        filmRepository.getFilmsByIds(ints).mapNotNull { filmsData ->
            when (filmsData) {
                is Data.Error -> {
                    _error.emit(filmsData.exception)
                    _isFilmsProgressBarVisible.tryEmit(false)
                    null
                }
                Data.Loading -> {
                    _isFilmsProgressBarVisible.tryEmit(true)
                    null
                }
                is Data.Success -> {
                    _isFilmsProgressBarVisible.tryEmit(false)
                    filmsData.model
                }
            }
        }
    }.asLiveData(Dispatchers.IO)

    private val _error = MutableSharedFlow<Throwable>()
    val error = _error.asLiveData(Dispatchers.IO)

    private val _isFilmsShown = MutableStateFlow(false)
    val isFilmsShown = _isFilmsShown.asLiveData(Dispatchers.IO)

    fun switchFilms() {
        _isFilmsShown.tryEmit(!_isFilmsShown.value)
    }

    private val _isFilmsProgressBarVisible = MutableStateFlow(false)
    val isFilmsProgressBarVisible: LiveData<Boolean> =
        _isFilmsShown.combine(_isFilmsProgressBarVisible) { isShown, isPBVisible ->
            isShown && isPBVisible
        }.asLiveData(Dispatchers.IO)

    fun switchFavourite() = person.value?.let {
        viewModelScope.launch {
            personRepository.updatePerson(
                it.copy(isFavourite = !it.isFavourite)
            )
        }
    }
}
