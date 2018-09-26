package br.com.zup.kotlingit2consul.application.services

interface WritableKeyValueStore {
    fun put(key: String, value: String)
    fun remove(key: String)
}