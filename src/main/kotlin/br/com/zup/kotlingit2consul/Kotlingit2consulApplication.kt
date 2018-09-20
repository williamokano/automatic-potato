package br.com.zup.kotlingit2consul

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class Kotlingit2consulApplication : CommandLineRunner {

    private companion object {
        val LOGGER = LoggerFactory.getLogger(this::class.java)!!
    }

    override fun run(vararg args: String?) {
        LOGGER.info("Application started!")
    }

}

fun main(args: Array<String>) {
    runApplication<Kotlingit2consulApplication>(*args)
}
