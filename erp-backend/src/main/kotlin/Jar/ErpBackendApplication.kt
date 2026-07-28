package Jar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ErpBackendApplication

fun main(args: Array<String>) {
	runApplication<ErpBackendApplication>(*args)
}
