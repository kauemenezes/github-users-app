package br.com.githubusersapp.user_domain.usecase

import br.com.githubusersapp.user_domain.repository.UserRepository

class GetUsersUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = userRepository.getUsers()
}
