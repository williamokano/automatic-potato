package br.com.zup.kotlingit2consul.services

interface ReadableKeyValueStore<out T> {
    fun getKeys(): Set<String>
    fun get(key: String): T
}
