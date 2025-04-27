package app.paramedicos.infrastructure.confing;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreaker patientCircuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // Umbral para abrir el circuito (50% de fallos)
                .waitDurationInOpenState(Duration.ofSeconds(10)) // Tiempo en estado abierto antes de pasar a medio abierto
                .permittedNumberOfCallsInHalfOpenState(5) // Número de llamadas permitidas en medio abierto
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // Tipo de ventana (por conteo)
                .slidingWindowSize(10) // Número de llamadas para calcular el umbral de fallos
                .minimumNumberOfCalls(5) // Mínimo de llamadas requeridas para calcular el umbral
                .failureRateThresholdInHalfOpenState(30) // Umbral de fallos en medio abierto para volver a abrir
                .build();

        return CircuitBreakerRegistry.of(config).circuitBreaker("patientCircuitBreaker");
    }
}