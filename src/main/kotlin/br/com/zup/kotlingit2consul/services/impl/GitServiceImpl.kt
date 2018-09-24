package br.com.zup.kotlingit2consul.services.impl

import br.com.zup.kotlingit2consul.services.GitService
import br.com.zup.kotlingit2consul.utils.GitUtils
import org.slf4j.LoggerFactory
import java.io.File

class GitServiceImpl(
    val keyPrefix: String,
    val repositoryUrl: String,
    val repositoryPath: String
) : GitService {
    private companion object {
        val LOGGER = LoggerFactory.getLogger(this::class.java)!!
    }

    override fun getKeys(): Set<String> {
        LOGGER.info("Getting info from $repositoryPath")
        return getFolderContents(repositoryPath, keyPrefix.trim('/')).toSet()
    }

    override fun get(key: String): String = key.removePrefix(keyPrefix).trim().let { normalizedKey ->
        val filePath = "$repositoryPath/$normalizedKey"
        LOGGER.info("Getting value from $filePath")
        File(filePath).readText()
    }

    override fun fetchOrUpdateRepository() {
        GitUtils.pullOrClone(repositoryUrl, repositoryPath)
    }

    private fun getFolderContents(path: String, prepend: String = ""): List<String> {
        val directory = File(path)
        val directoryContents = directory.listFiles()
        if (directoryContents != null) {
            val files = directoryContents.filter { it.isFile && it.name.startsWith(".").not() }
            val directories = directoryContents.filter { it.isDirectory && it.name.startsWith(".").not() }

            val strFiles = files.map { "$prepend/${it.name}".trim('/') }
            val strDirectories = directories.flatMap { getFolderContents(it.path, "$prepend/${it.name.trim('/')}") }

            return mutableListOf<String>()
                .apply { addAll(strFiles) }
                .apply { addAll(strDirectories) }
        }

        return emptyList()
    }

    private fun buildKeys(key: String) =
        listOf(keyPrefix.trim('/'), '/', key)
            .joinToString("")
            .trim('/')
}
