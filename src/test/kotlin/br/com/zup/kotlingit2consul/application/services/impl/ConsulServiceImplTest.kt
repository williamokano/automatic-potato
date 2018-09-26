package br.com.zup.kotlingit2consul.application.services.impl

import br.com.zup.kotlingit2consul.application.BaseTest
import com.nhaarman.mockito_kotlin.verify
import com.orbitz.consul.Consul
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext

@DirtiesContext
class ConsulServiceImplTest : BaseTest() {

    @Autowired
    private lateinit var consul: Consul

    @Test
    fun `should get keys`() {
        val keys = ConsulServiceImpl(consul).getKeys()
        assert(keys == setOf("a", "b", "c"))
    }

    @Test
    fun `should get existing key`() {
        val value = ConsulServiceImpl(consul).get("existing_key")
        assert(value == "existing_value")
    }

    @Test(expected = RuntimeException::class)
    fun `should not get existing key`() {
        ConsulServiceImpl(consul).get("non_existing_key")
    }

    @Test
    fun `should put value`() {
        ConsulServiceImpl(consul).put("a", "b")
        verify(consul.keyValueClient()).putValue("a", "b")
    }

    @Test
    fun `should remove value`() {
        ConsulServiceImpl(consul).remove("c")
        verify(consul.keyValueClient()).deleteKey("c")
    }
}