package br.com.githubusersapp.user_data.di

import br.com.githubusersapp.user_data.BuildConfig
import br.com.githubusersapp.user_data.api.UserService
import br.com.githubusersapp.user_data.datasource.UserDataSource
import br.com.githubusersapp.user_data.datasource.UserDataSourceImpl
import br.com.githubusersapp.user_data.repository.UserRepositoryImpl
import br.com.githubusersapp.user_domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideUserService(client: OkHttpClient): UserService {
        return Retrofit.Builder()
            .baseUrl(UserService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userDataSource: UserDataSource,
        dispatcher: CoroutineDispatcher
    ): UserRepository {
        return UserRepositoryImpl(
            userDataSource = userDataSource,
            dispatcher = dispatcher
        )
    }

    @Provides
    @Singleton
    fun provideUserDataSource(
        userService: UserService
    ): UserDataSource {
        return UserDataSourceImpl(
            userService = userService
        )
    }

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}