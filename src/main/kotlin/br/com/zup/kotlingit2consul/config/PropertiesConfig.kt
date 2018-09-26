package br.com.zup.kotlingit2consul.config

import br.com.zup.kotlingit2consul.application.services.ConsulService
import br.com.zup.kotlingit2consul.application.services.GitService
import br.com.zup.kotlingit2consul.application.services.PropertiesService
import br.com.zup.kotlingit2consul.application.services.impl.ConsulServiceImpl
import br.com.zup.kotlingit2consul.application.services.impl.GitServiceImpl
import br.com.zup.kotlingit2consul.application.services.impl.PropertiesServiceImpl
import com.orbitz.consul.Consul
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PropertiesConfig {
    @Bean
    fun consulService(consul: Consul): ConsulService =
        ConsulServiceImpl(consul)

    @Bean
    fun gitService(
        @Value("\${consul.prepended.to.key}") prependKey: String,
        @Value("\${git.config.url}") gitConfigUrl: String,
        @Value("\${git.config.branch}") gitConfigBranch: String,
        @Value("\${git.config.clone.path}") gitConfigPath: String
    ): GitService =
        GitServiceImpl(
            keyPrefix = prependKey,
            repositoryUrl = gitConfigUrl,
            repositoryPath = gitConfigPath,
            repositoryBranch = gitConfigBranch
        )

    @Bean
    fun propertiesService(
        @Value("\${git.values.url}") repositoryUrl: String,
        @Value("\${git.values.clone.path}") repositoryPath: String,
        @Value("\${git.values.branch}") repositoryBranch: String
    ): PropertiesService =
        PropertiesServiceImpl(
            repositoryUrl = repositoryUrl,
            repositoryPath = repositoryPath,
            repositoryBranch = repositoryBranch
        )
}