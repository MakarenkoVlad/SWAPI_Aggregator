package vlad.makarenko.swapiaggregator.data.local

import android.content.SharedPreferences
import dagger.hilt.android.scopes.ViewModelScoped
import vlad.makarenko.swapiaggregator.utils.boolean
import javax.inject.Inject

class PreferencesManager @Inject constructor(sharedPreferences: SharedPreferences) {

    var isFilmsRequested by sharedPreferences.boolean(IS_FILMS_REQUESTED_KEY)

    companion object {
        const val IS_FILMS_REQUESTED_KEY = "isFilmsRequested"
    }
}
