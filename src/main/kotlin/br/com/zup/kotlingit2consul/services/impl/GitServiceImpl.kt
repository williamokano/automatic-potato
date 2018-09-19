package br.com.zup.kotlingit2consul.services.impl

import br.com.zup.kotlingit2consul.services.GitService
import org.springframework.stereotype.Service

@Service
class GitServiceImpl : GitService {
    private val mockKv = mapOf(
        "config/application/data" to "realwave",
        "config/rw-iam-app/data" to "I have no content"
    )

    override fun getKeys(): Set<String> {
        return mockKv.keys
    }

    override fun get(key: String): String {
        return mockKv.getOrElse(key) {
            throw RuntimeException("Key $key do not exist")
        }
    }
}