package br.com.githubusersapp.user_data.mapper

import br.com.domain.model.Repo
import br.com.githubusersapp.user_data.model.RepoResponse
import br.com.githubusersapp.user_domain.util.Mapper

class RepoResponseMapper : Mapper<RepoResponse, Repo?> {
    override fun map(source: RepoResponse): Repo {
        return Repo(
            id = source.id,
            name = source.name.orEmpty(),
            fullName = source.fullName.orEmpty(),
            url = source.url.orEmpty(),
        )
    }
}
