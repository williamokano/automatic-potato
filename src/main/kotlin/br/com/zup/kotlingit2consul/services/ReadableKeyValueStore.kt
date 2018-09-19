package br.com.zup.kotlingit2consul.services

interface ReadableKeyValueStore {
    fun getKeys(): Set<String>
    fun get(key: String): String
}