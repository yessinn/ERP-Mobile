package Jar.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtUtil {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(
        "this-is-a-very-long-secret-key-for-jwt-signing-change-later".toByteArray()
    )

    private val expirationMillis = 1000 * 60 * 60 * 10 // 10 hours

    fun generateToken(email: String, role: String): String {
        return Jwts.builder()
            .subject(email)
            .claim("role", role)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationMillis))
            .signWith(secretKey)
            .compact()
    }

    fun extractEmail(token: String): String {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }

    fun extractRole(token: String): String {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload["role"] as String
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }
}