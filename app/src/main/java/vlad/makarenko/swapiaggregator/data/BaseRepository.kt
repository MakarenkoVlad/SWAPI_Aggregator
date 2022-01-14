package vlad.makarenko.swapiaggregator.data

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import vlad.makarenko.swapiaggregator.data.model.Data

abstract class BaseRepository {

    protected fun <T> buildDataFlow(block: suspend FlowCollector<Data<T>>.() -> Unit) = flow {
        emit(Data.Loading)
        try {
            block()
        } catch (e: Exception) {
            emit(Data.Error(e))
        }
    }
}