package demo

import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration class Config {
    @Bean fun catsService() = CatsService("https://cataas.com/")

    @Bean fun jackson2ObjectMapperBuilder() =
        Jackson2ObjectMapperBuilder().modules(
            KotlinModule.Builder()
                .configure(KotlinFeature.StrictNullChecks, true)
                .build()
        )
}