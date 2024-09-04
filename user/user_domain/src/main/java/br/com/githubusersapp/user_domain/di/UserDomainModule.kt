package br.com.githubusersapp.user_domain.di

import br.com.githubusersapp.user_domain.repository.UserRepository
import br.com.githubusersapp.user_domain.usecase.GetUserDetailsUseCase
import br.com.githubusersapp.user_domain.usecase.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UserDomainModule {
    @ViewModelScoped
    @Provides
    fun provideGetUsersUseCase(userRepository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(userRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideGetUserDetailsUseCase(userRepository: UserRepository): GetUserDetailsUseCase {
        return GetUserDetailsUseCase(userRepository)
    }
}
