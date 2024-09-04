package br.com.githubusersapp.user_data.mapper

import br.com.domain.model.User
import br.com.githubusersapp.user_data.model.UserResponse
import br.com.githubusersapp.user_domain.util.Mapper

class UserResponseMapper : Mapper<UserResponse, User?> {
    override fun map(source: UserResponse): User {
        return User(
            id = source.id,
            name = source.name.orEmpty(),
            login = source.login.orEmpty(),
            avatarUrl = source.avatarUrl.orEmpty(),
        )
    }
}
