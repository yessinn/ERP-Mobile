package Jar

import Jar.dto.RegisterRequest
import Jar.dto.UserResponse
import Jar.model.User
import Jar.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import Jar.dto.LoginRequest
import Jar.security.JwtUtil

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<UserResponse> {
        val encryptedPassword = passwordEncoder.encode(request.password)

        val newUser = User(
            email = request.email,
            password = encryptedPassword,
            role = request.role
        )

        val savedUser = userRepository.save(newUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(savedUser))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<String> {
        val user = userRepository.findByEmail(request.email)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found")

        val passwordMatches = passwordEncoder.matches(request.password, user.password)

        return if (passwordMatches) {
            ResponseEntity.ok(jwtUtil.generateToken(user.email, user.role))
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password")
        }
    }
}