package vlad.makarenko.swapiaggregator.data.model

import java.lang.Exception

sealed interface Data<out M> {
    data class Success<M>(val model: M) : Data<M>
    data class Error(val exception: Exception) : Data<Nothing>
    object Loading : Data<Nothing>
}
