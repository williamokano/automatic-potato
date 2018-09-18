package br.com.zup.kotlingit2consul.kotlingit2consul.services

import com.orbitz.consul.Consul
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduler(
    private val consul: Consul
) {
    private val kvClient by lazy { consul.keyValueClient() }
    @Scheduled(fixedDelayString = "\${poll.interval.ms}")
    fun sayHello() {
        val keys = kvClient.getKeys("iam")
    }
}