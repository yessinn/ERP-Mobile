package Jar

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/api/hello")
    fun sayHello(): String {
        return "Hello from the ERP backend!"
    }
}