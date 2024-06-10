package org.junyoung.redisconcurrency

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RedisConcurrencyApplication

fun main(args: Array<String>) {
    runApplication<RedisConcurrencyApplication>(*args)
}
