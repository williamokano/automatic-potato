package br.com.zup.kotlingit2consul.services.impl

import br.com.zup.kotlingit2consul.services.ConsulService
import com.orbitz.consul.Consul
import org.slf4j.LoggerFactory

class ConsulServiceImpl(
    val consul: Consul
) : ConsulService {
    private companion object {
        val LOGGER = LoggerFactory.getLogger(this::class.java)!!
    }

    private val kvClient by lazy { consul.keyValueClient() }

    override fun getKeys(): Set<String> {
        return kvClient.getKeys("/").toSet()
    }

    override fun get(key: String): String {
        LOGGER.debug("Trying to get key $key")
        return kvClient.getValueAsString(key).orElseThrow {
            RuntimeException("Failed to get key $key")
        }
    }

    override fun put(key: String, value: String) {
        LOGGER.debug("Trying to put key $key with some value")
        kvClient.putValue(key, value)
    }

    override fun remove(key: String) {
        LOGGER.warn("Removing key $key from consul.")
        kvClient.deleteKey(key)
    }
}