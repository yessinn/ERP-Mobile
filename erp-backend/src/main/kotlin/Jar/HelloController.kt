package Jar

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/api/hello")
    fun sayHello(): String {
        return "Hello from the ERP backend!"
    }

    @GetMapping("/api/protected")
    fun protectedRoute(): String {
        return "If you see this, your JWT token is valid!"
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/admin-only")
    fun adminOnlyRoute(): String {
        return "Welcome, admin! This route is restricted to ADMIN role only."
    }
}