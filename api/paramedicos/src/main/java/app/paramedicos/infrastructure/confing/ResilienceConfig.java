package app.paramedicos.infrastructure.confing;


import app.paramedicos.domain.exception.MedicalRecordException;
import app.paramedicos.domain.exception.PatientNotFoundException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig defaultConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // Se abre si 50% de llamadas fallan
                .waitDurationInOpenState(Duration.ofSeconds(10)) // 10s en estado OPEN
                .slidingWindowSize(6) // Últimas 6 llamadas
                .minimumNumberOfCalls(5) // Necesita al menos 5 para evaluar
                .permittedNumberOfCallsInHalfOpenState(2) // 2 llamadas en Half-Open
                .recordExceptions(
                        RuntimeException.class,
                        MedicalRecordException.class,
                        PatientNotFoundException.class
                ) // Activar resiliencia ante Runtime y tus errores de negocio
                .ignoreExceptions(
                        IllegalArgumentException.class
                ) // Ignorar errores de validación de datos
                .build();
        return CircuitBreakerRegistry.of(defaultConfig);
    }

    @Bean
    public CircuitBreaker medicalRecordCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("medicalRecordService");
    }

    @Bean
    public CircuitBreaker patientServiceCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("patientService");
    }
}
