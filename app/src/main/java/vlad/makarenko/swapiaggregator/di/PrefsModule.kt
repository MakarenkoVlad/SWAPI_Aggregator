package vlad.makarenko.swapiaggregator.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vlad.makarenko.swapiaggregator.utils.Constants

@InstallIn(SingletonComponent::class)
@Module
object PrefsModule {

    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context) =
        context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
}
