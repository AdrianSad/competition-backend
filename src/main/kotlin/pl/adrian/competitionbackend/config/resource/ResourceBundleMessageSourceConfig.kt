package pl.adrian.competitionbackend.config.resource

import net.tuziemiec.utils.Utils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import java.util.*

@Configuration
class ResourceBundleMessageSourceConfig {

    @Bean
    fun messageSource(): ResourceBundleMessageSource {
        val source = ResourceBundleMessageSource()
        source.setBasenames("messages/errors")
        source.setUseCodeAsDefaultMessage(true)
        source.setDefaultEncoding("UTF-8")
        return source
    }
}