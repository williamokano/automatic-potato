package br.com.zup.kotlingit2consul.services.impl

import br.com.zup.kotlingit2consul.services.PropertiesService
import br.com.zup.kotlingit2consul.utils.GitUtils
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class PropertiesServiceImpl(
    private val repositoryUrl: String,
    private val repositoryPath: String
) : PropertiesService {
    private val mockProps = mapOf(
        "name" to "William",
        "lastname" to "Okano",
        "test.with.dots" to "it worked",
        "replaced" to "Zoppppppeopeorpoer"
    )

    private val objectMapper by lazy { ObjectMapper() }

    private companion object {
        val LOGGER = LoggerFactory.getLogger(this::class.java)!!
    }

    override fun getKeys(): Set<String> =
        mockProps.keys

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

    override fun updateProperties() =
        GitUtils.pullOrClone(repositoryUrl, repositoryPath)

    override fun getProperties(): Map<String, String> =
        mockProps

    private fun sanitizeKey(key: String) =
        key.removePrefix("config").removeSuffix("data").trim('/')
}
