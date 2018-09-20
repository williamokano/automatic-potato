package br.com.zup.kotlingit2consul.services.impl

import br.com.zup.kotlingit2consul.services.ConsulService
import org.slf4j.LoggerFactory

class ConsulServiceImpl : ConsulService {
    private companion object {
        val LOGGER = LoggerFactory.getLogger(this::class.java)!!
    }

    private val mutKV = mutableMapOf<String, String>()

    override fun getKeys(): Set<String> {
        return mutKV.keys
    }

    override fun get(key: String): String {
        LOGGER.debug("Trying to get key $key")
        return mutKV.getOrElse(key) {
            throw RuntimeException("Key $key not found")
        }
    }

    override fun put(key: String, value: String) {
        LOGGER.debug("Trying to put key $key with some value")
        mutKV[key] = value
    }

    override fun remove(key: String) {
        LOGGER.warn("Removing key $key from consul.")
        mutKV.remove(key)
    }
}