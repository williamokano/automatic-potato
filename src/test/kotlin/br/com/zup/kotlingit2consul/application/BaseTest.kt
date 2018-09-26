package br.com.zup.kotlingit2consul.application

import br.com.zup.kotlingit2consul.config.ConsulTestConfig
import br.com.zup.kotlingit2consul.config.PropertiesConfig
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@ContextConfiguration(classes = [ConsulTestConfig::class, PropertiesConfig::class])
abstract class BaseTest