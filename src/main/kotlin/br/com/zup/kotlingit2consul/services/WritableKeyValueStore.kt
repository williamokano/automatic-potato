package br.com.zup.kotlingit2consul.services

interface WritableKeyValueStore {
    fun put(key: String, value: String)
    fun remove(key: String)
}