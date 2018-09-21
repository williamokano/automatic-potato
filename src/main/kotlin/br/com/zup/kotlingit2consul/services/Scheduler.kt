package br.com.zup.kotlingit2consul.services

import org.apache.commons.text.StringSubstitutor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduler(
    private val consulService: ConsulService,
    private val gitService: GitService,
    private val propertiesService: PropertiesService
) {
    private companion object {
        val LOGGER = LoggerFactory.getLogger(this::class.java)!!
    }

    @Value("\${should.clean.consul.keys:false}")
    private var shouldCleanConsulKeys: Boolean? = false

    @Scheduled(fixedDelayString = "\${poll.interval.ms}")
    fun updateConsulProperties() {
        LOGGER.info("Running updateConsulProperties. Getting keys")
        gitService.updateRepository()
        val gitKVKeys = gitService.getKeys()

        gitKVKeys.forEach { key ->
            LOGGER.info("Key found '$key'. Updating consul with key value")
            getValue(key).let { value ->
                LOGGER.info("$key: $value")
                consulService.put(key, value)
            }
        }

        if (shouldCleanConsulKeys()) {
            val consulKeys = consulService.getKeys()
            consulKeys.asSequence().minus(gitKVKeys).toList().forEach { key ->
                consulService.remove(key)
            }
        }
    }

    private fun shouldCleanConsulKeys() = shouldCleanConsulKeys ?: false

    private fun getValue(key: String): String {
        val valueFromGit = gitService.get(key)
        val properties = propertiesService.getProperties()
        return StringSubstitutor(properties).replace(valueFromGit)
    }
}