package br.com.zup.kotlingit2consul.kotlingit2consul

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class Kotlingit2consulApplication : CommandLineRunner {
    override fun run(vararg args: String?) {
        println("RUN TO THE HILLS")
    }

}

fun main(args: Array<String>) {
    runApplication<Kotlingit2consulApplication>(*args)
}
