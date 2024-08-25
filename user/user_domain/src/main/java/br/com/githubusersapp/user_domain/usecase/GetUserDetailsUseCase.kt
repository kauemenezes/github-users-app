package br.com.githubusersapp.user_domain.usecase

import br.com.githubusersapp.user_domain.repository.UserRepository

class GetUserDetailsUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userLogin: String) =
        userRepository.getUserDetails(userLogin)
}