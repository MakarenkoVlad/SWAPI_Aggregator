package vlad.makarenko.swapiaggregator.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vlad.makarenko.swapiaggregator.data.local.Database
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room
            .databaseBuilder(context, Database::class.java, Database::class.java.canonicalName!!)
            .build()

    @Provides
    fun providePersonDao(database: Database) = database.personDao()

    @Provides
    fun provideFilmDao(database: Database) = database.filmDao()
}
