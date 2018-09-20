package br.com.zup.kotlingit2consul.services.impl

import br.com.zup.kotlingit2consul.services.PropertiesService

class PropertiesServiceImpl : PropertiesService {
    private val mockProps = mapOf(
        "name" to "William",
        "lastname" to "Okano",
        "test.with.dots" to "it worked"
    )

    override fun getKeys(): Set<String> =
        mockProps.keys

    override fun get(key: String): String =
        mockProps.getOrDefault(key, key)

    override fun getProperties(): Map<String, String> =
        mockProps
}