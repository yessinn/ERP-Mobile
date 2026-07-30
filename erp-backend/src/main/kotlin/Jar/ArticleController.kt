package Jar

import Jar.dto.ArticleRequest
import Jar.model.Articles
import Jar.repository.ArticleRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/articles")
class ArticleController(
    private val articleRepository: ArticleRepository
) {

    @GetMapping
    fun getAllArticles(): List<Articles> {
        return articleRepository.findAll()
    }

    @GetMapping("/{id}")
    fun getArticleById(@PathVariable id: Long): Articles? {
        return articleRepository.findById(id).orElse(null)
    }

    @PostMapping
    fun createArticle(@RequestBody request: ArticleRequest): Articles {
        val newArticles = Articles(
            name = request.name,
            reference = request.reference,
            price = request.price,
            quantity = request.quantity,
            category = request.category
        )
        return articleRepository.save(newArticles)
    }
    @PutMapping("/{id}")
    fun updateArticle(@PathVariable id: Long, @RequestBody request: ArticleRequest): Articles? {
        val existingArticle = articleRepository.findById(id).orElse(null) ?: return null

        val updatedArticle = Articles(
            id = existingArticle.id,
            name = request.name,
            reference = request.reference,
            price = request.price,
            quantity = request.quantity,
            category = request.category
        )

        return articleRepository.save(updatedArticle)
    }

    @DeleteMapping("/{id}")
    fun deleteArticle(@PathVariable id: Long) {
        articleRepository.deleteById(id)
    }
}