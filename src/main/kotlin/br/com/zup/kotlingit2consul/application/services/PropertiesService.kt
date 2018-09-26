package br.com.zup.kotlingit2consul.application.services

interface PropertiesService :
    ReadableKeyValueStore<Map<String, String>> {
    fun updateProperties()
}
