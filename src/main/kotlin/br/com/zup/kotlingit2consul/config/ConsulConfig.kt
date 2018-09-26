package br.com.zup.kotlingit2consul.config

import com.google.common.net.HostAndPort
import com.orbitz.consul.Consul
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConsulConfig {
    @Value("\${consul.host}")
    private lateinit var host: String

    @Value("\${consul.port}")
    private var port: Int? = null

    @Bean
    @ConditionalOnMissingBean
    fun consulClient(): Consul =
        Consul.builder()
            .withHostAndPort(HostAndPort.fromParts(host, port!!))
            .build()
}
