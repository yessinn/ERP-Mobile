package Jar.model

import jakarta.persistence.*

@Entity
@Table(name = "articles")
class Articles(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String = "",

    @Column(unique = true, nullable = false)
    val reference: String = "",

    @Column(nullable = false)
    val price: Double = 0.0,

    @Column(nullable = false)
    val quantity: Int = 0,

    @Column
    val category: String = ""
)