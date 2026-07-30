package Jar

import Jar.dto.RegisterRequest
import Jar.model.User
import Jar.repository.UserRepository
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
    fun register(@RequestBody request: RegisterRequest): User {
        val encryptedPassword = passwordEncoder.encode(request.password)

        val newUser = User(
            email = request.email,
            password = encryptedPassword,
            role = request.role
        )

        return userRepository.save(newUser)
    }
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): String {
        val user = userRepository.findByEmail(request.email)
            ?: return "User not found"

        val passwordMatches = passwordEncoder.matches(request.password, user.password)

        return if (passwordMatches) {
            jwtUtil.generateToken(user.email, user.role)
        } else {
            "Invalid password"
        }
    }
}