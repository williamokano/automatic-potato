package br.com.zup.kotlingit2consul.services

import org.springframework.stereotype.Service

@Service
interface ConsulService : ReadableKeyValueStore, WritableKeyValueStore