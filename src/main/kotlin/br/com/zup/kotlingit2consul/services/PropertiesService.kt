package br.com.zup.kotlingit2consul.services

interface PropertiesService : ReadableKeyValueStore {
    fun getProperties(): Map<String, String>
}