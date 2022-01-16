package vlad.makarenko.swapiaggregator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import vlad.makarenko.swapiaggregator.data.PersonRepository
import vlad.makarenko.swapiaggregator.data.model.Person
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(private val personRepository: PersonRepository) : ViewModel() {

    private val query = MutableStateFlow<String?>(null)

    val people = query.flatMapLatest {
        personRepository.getPeople(it)
    }.cachedIn(viewModelScope)

    fun search(query: String?) {
        this.query.tryEmit(
            if (query?.isNotBlank() == true)
                query
            else
                null
        )
    }

    fun updatePerson(person: Person?) = person?.let {
        viewModelScope.launch(Dispatchers.IO) {
            personRepository.updatePerson(it)
        }
    }
}
