package br.com.zup.kotlingit2consul.services.impl

import br.com.zup.kotlingit2consul.services.GitService
import br.com.zup.kotlingit2consul.utils.GitUtils

class GitServiceImpl(
    val keyPrefix: String,
    val repositoryUrl: String,
    val repositoryPath: String
) : GitService {
    private val mockKv = mapOf(
        "application/data" to "realwave",
        "rw-iam-app/data" to "I have no content to \${name} \${error}",
        "rw-router/data" to "something that sould be \${replaced}"
    )

    override fun getKeys(): Set<String> {
        return mockKv.keys.asSequence().map(this::buildKeys).toSet()
    }

    override fun get(key: String): String = key.removePrefix(keyPrefix).trim().let { normalizedKey ->
        mockKv.getOrElse(normalizedKey) {
            throw RuntimeException("Key $normalizedKey do not exist")
        }
    }

    override fun updateRepository() {
        GitUtils.pullOrClone(repositoryUrl, repositoryPath)
    }

    private fun buildKeys(key: String) =
        listOf(keyPrefix.trim('/'), '/', key)
            .joinToString("")
            .trim('/')
}