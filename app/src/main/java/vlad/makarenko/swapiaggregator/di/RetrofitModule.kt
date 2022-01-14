package vlad.makarenko.swapiaggregator.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import vlad.makarenko.swapiaggregator.data.remote.SWAPIService
import vlad.makarenko.swapiaggregator.utils.Constants
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Provides
    fun provideOkHttpClient() =
        OkHttpClient
            .Builder()
            .build()

    @Provides
    fun provideMoshiConverterFactory(): Converter.Factory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, converterFactory: Converter.Factory) =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    fun provideSWAPIService(retrofit: Retrofit): SWAPIService = retrofit.create()
}
