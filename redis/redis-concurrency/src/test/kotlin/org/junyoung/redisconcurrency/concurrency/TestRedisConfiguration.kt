package org.junyoung.redisconcurrency.concurrency

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import redis.embedded.RedisServer

@TestConfiguration
class TestRedisConfiguration {

    lateinit var redisServer: RedisServer
    private val testRedisHost = "127.0.0.1"
    private val testRedisPort = 6370

    companion object {
        const val DEFAULT_DATABASE = 0
    }

    @PostConstruct
    fun postConstruct() {
        println("embedded redis start!")
        redisServer = RedisServer(testRedisPort)
        redisServer.start()
    }

    @PreDestroy
    fun preDestroy() {
        println("embedded redis stop!")
        redisServer.stop()
    }

    fun lettuceConnectionFactory(database: Int): LettuceConnectionFactory {
        val redisConfig = RedisStandaloneConfiguration().apply {
            this.hostName = testRedisHost
            this.port = testRedisPort
            this.database = database
        }

        return LettuceConnectionFactory(redisConfig).apply {
            this.afterPropertiesSet()
        }
    }

    @Bean
    fun testStringRedisTemplate(): StringRedisTemplate {
        return StringRedisTemplate().apply {
            this.setConnectionFactory(lettuceConnectionFactory(DEFAULT_DATABASE))
            this.afterPropertiesSet()
        }
    }
}