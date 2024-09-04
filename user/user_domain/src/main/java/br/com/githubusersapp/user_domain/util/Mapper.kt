package br.com.githubusersapp.user_domain.util

interface Mapper<S, T> {
    fun map(source: S): T
}
