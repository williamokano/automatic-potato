package br.com.zup.kotlingit2consul.services

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class Scheduler(
    private val consulService: ConsulService,
    private val gitService: GitService
) {
    private companion object {
        val LOGGER = LoggerFactory.getLogger(this::class.java)!!
    }

    @Value("\${should.clean.consul.keys:false}")
    private var shouldCleanConsulKeys: Boolean? = false

    @Scheduled(fixedDelayString = "\${poll.interval.ms}")
    fun updateConsulProperties() {
        LOGGER.info("Running updateConsulProperties. Getting keys")
        val gitKVKeys = gitService.getKeys()

        gitKVKeys.forEach { key ->
            LOGGER.info("Key found '$key'. Updating consul with key value")
            gitService.get(key).let { value ->
                consulService.put(key, value)
            }
        }

        if (shouldCleanConsulKeys()) {
            consulService.getKeys().minus(gitKVKeys).forEach { key ->
                LOGGER.warn("Key $key do not exist on git anymore. Deleting from consul!!!!")
                consulService.remove(key)
            }
        }
    }

    private fun shouldCleanConsulKeys() = shouldCleanConsulKeys ?: false

    @Scheduled(fixedDelay = 1000, initialDelay = 20000)
    fun justMockingWeirdBehavior() {
        val randomKey = UUID.randomUUID().toString()
        LOGGER.warn("ADDING NONEXISTING KEY FROM GIT '$randomKey'")
        consulService.put(randomKey, UUID.randomUUID().toString())
    }
}