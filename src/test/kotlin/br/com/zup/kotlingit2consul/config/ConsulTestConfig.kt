package br.com.zup.kotlingit2consul.config

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import com.orbitz.consul.Consul
import com.orbitz.consul.KeyValueClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class ConsulTestConfig {
    @Bean
    fun consulClient(): Consul =
        mockKvClient().let { mock ->
            mock {
                on { keyValueClient() } doReturn mock
            }
        }

    private fun mockKvClient(): KeyValueClient = mock {
        on { getKeys("/") } doReturn listOf("a", "b", "c")
        on { getValueAsString("existing_key") } doReturn Optional.of("existing_value")
        on { getValueAsString("non_existing_key") } doThrow RuntimeException::class
    }
}