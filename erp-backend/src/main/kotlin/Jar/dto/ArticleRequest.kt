package Jar.dto

data class ArticleRequest(
    val name: String,
    val reference: String,
    val price: Double,
    val quantity: Int,
    val category: String
)