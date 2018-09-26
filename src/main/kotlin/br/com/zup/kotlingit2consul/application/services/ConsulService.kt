package br.com.zup.kotlingit2consul.application.services

import org.springframework.stereotype.Service

@Service
interface ConsulService : ReadableKeyValueStore<String>,
    WritableKeyValueStore
