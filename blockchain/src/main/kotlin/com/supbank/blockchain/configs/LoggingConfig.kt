package com.supbank.blockchain.configs

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

/**
 * Creation d'annotation pour la creation de logger
 */
@Configuration
class LoggingConfig {

    @Bean
    @Scope("prototype")
    fun logger(injectionPoint: InjectionPoint): Logger {
        return LoggerFactory.getLogger(
                injectionPoint.methodParameter?.containingClass
                        ?:injectionPoint.field?.declaringClass)
    }
}