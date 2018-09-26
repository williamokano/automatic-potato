package br.com.zup.kotlingit2consul.application.services.impl

import br.com.zup.kotlingit2consul.application.services.PropertiesService
import br.com.zup.kotlingit2consul.application.utils.GitUtils
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class PropertiesServiceImpl(
    private val repositoryUrl: String,
    private val repositoryPath: String,
    private val repositoryBranch: String
) : PropertiesService {
    private val objectMapper by lazy { ObjectMapper() }

    private companion object {
        val LOGGER = LoggerFactory.getLogger(this::class.java)!!
    }

    override fun getKeys(): Set<String> = TODO("Not implemented")

    override fun get(key: String): Map<String, String> {
        val sanitizedKey = sanitizeKey(key)
        val filePath = "$repositoryPath/$sanitizedKey/values.json"
        LOGGER.info("Trying to get the key $sanitizedKey on filePath $filePath")
        val properties = when (Files.exists(Paths.get(filePath))) {
            true -> {
                val valuesAsString = File(filePath).readText()
                objectMapper.readValue<Map<String, String>>(
                    valuesAsString,
                    object : TypeReference<Map<String, String>>() {})
            }
            false -> emptyMap()
        }
        LOGGER.info("Done")
        return properties
    }

    override fun updateProperties() {
        GitUtils.pullOrClone(repositoryUrl, repositoryPath)
        GitUtils.checkout(repositoryPath, repositoryBranch)
    }

    private fun sanitizeKey(key: String) =
        key.removePrefix("config").removeSuffix("data").trim('/')
}
