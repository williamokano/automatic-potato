package br.com.zup.kotlingit2consul.services.impl

import br.com.zup.kotlingit2consul.services.GitService

class GitServiceImpl(
    val keyPrefix: String
) : GitService {

    private val mockKv = mapOf(
        "application/data" to "realwave",
        "rw-iam-app/data" to "I have no content"
    )

    override fun getKeys(): Set<String> {
        return mockKv.keys.asSequence().map(this::buildKeys).toSet()
    }

    override fun get(key: String): String = key.removePrefix(keyPrefix).trim().let { normalizedKey ->
        mockKv.getOrElse(normalizedKey) {
            throw RuntimeException("Key $normalizedKey do not exist")
        }
    }

    private fun buildKeys(key: String) =
        listOf(keyPrefix.trim('/'), '/', key)
            .joinToString("")
            .trim('/')
}