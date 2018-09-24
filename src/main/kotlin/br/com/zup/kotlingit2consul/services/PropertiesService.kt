package br.com.zup.kotlingit2consul.services

interface PropertiesService : ReadableKeyValueStore<Map<String, String>> {
    fun updateProperties()
    fun getProperties(): Map<String, String>
}
