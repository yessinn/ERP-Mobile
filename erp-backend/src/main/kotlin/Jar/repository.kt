package Jar.repository

import Jar.model.User
import Jar.model.Articles

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?
}
interface ArticleRepository : JpaRepository<Articles, Long> {

    fun findByReference(reference: String): Articles?
}