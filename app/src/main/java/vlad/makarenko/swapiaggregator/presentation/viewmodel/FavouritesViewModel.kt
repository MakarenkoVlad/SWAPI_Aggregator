package vlad.makarenko.swapiaggregator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import vlad.makarenko.swapiaggregator.data.PersonRepository
import vlad.makarenko.swapiaggregator.data.model.Person
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class FavouritesViewModel @Inject constructor(private val repository: PersonRepository) :
    ViewModel() {

    fun updatePerson(person: Person?) =
        person?.let { viewModelScope.launch(Dispatchers.IO) { repository.updatePerson(it) } }

    val people = repository.getFavouritePeople().asLiveData(Dispatchers.IO)
}
