package com.supbank.blockchain.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.RouterFunction


@Configuration
@EnableWebFlux
class WebConfig {
    fun corsConfigurer(): WebFluxConfigurer {
        return object : WebFluxConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET")
                        .allowedMethods("POST")
            }
        }
    }

    /**
     * Function used to redirect / to index and js or css resources to the static folder
     */
    @Bean
    fun webAppRouter(@Value("classpath:/static/index.html") html: Resource): RouterFunction<ServerResponse> {
        return router {
            GET("/") { request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).syncBody(html) }
            GET("/wallet") { request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).syncBody(html) }
            resources("/*.js", ClassPathResource("static/"))
            resources("/*.css", ClassPathResource("static/"))
            resources("/assets/*.png", ClassPathResource("static/assets/"))
        }
    }
}
