package com.wallapop.marsRover

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.wallapop.marsRover"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
