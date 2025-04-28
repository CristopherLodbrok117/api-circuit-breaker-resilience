# APP paramédicos con Resilience (client + API)



### API Dependencies:
- Spring Web
- Spring Data JPA 
- MySQL Driver
- Lombok
- Resilience4J
- Spring Cloud

### Android APP Dependencies:
- React native
- Expo
- etc... [mas dependencias](https://github.com/CristopherLodbrok117/AppMedicos/blob/4937f55ae0213a9bdace65ede9da779587c2d5d0/package.json)

### Un poco sobre la app
Se trata de una aplicación dirigida al cuerpo de paramédicos de UDG CUCEI. Les facilita el registro de pacientes, capturando información de condición física, traslado, control de 
insumos y captura de firmas digitales.

Pendiente a implementar:
- Base de datos local
- Generador de PDF (documentos legales)
- Actuator (monitoreo de salud de la API)

<br>

### Apariencia de la Aplicación

[Repositorio](https://github.com/CristopherLodbrok117/AppMedicos/tree/api) de la aplicación movíl
Estas son algunas de sus pantallas principales

#### Pantalla principal
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/01%20home.png" alt="Description" width="350">

<br>

#### Evaluación general
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/02%20medical%20record.png" alt="medical record" width="350">

<br>

#### Evaluacion del paciente
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/03%20Eva%C3%B1uacion.png" alt="Evaluación" width="350">

<br>

#### Evaluacion inicial
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/04%20evaluacion%20inicial.png" alt="evaluacion inicial" width="350">

<br>

#### Exploracion fisica
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/05%20exploracion%20fisica.png" alt="Exploracion fisica" width="350">

<br>

#### Condición del paciente
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/06%20condicion.png" alt="condicion" width="350">

<br>

#### Traslado del paciente
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/07%20traslado.png" alt="traslado" width="350">

<br>

#### Control de insumos
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/08%20insumos.png" alt="insumos" width="350">

<br>

#### Selector de fecha
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/09%20fecha.png" alt="fecha" width="350">

<br>

#### Selector de hora
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/10%20hora.png" alt="hora" width="350">

<br>

#### Varios modulos de fecha
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/11%20mas%20fechas.png" alt="mas fechas" width="350">

<br>

#### Uso de modales
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/12%20modales.png" alt="modales" width="350">

<br>

#### Scroll pickers para uso de gestos
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/13%20scroll%20pickers.png" alt="scroll pickers" width="350">

<br>

#### Firmas digitales
<img src="https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/190c53affb4be9c95d58c6e7a9aedfd068dee692/screenshots/14%20firma%20digital.png" alt="firma digital" width="350">

<br>


## Vistazo al Circuit Breaker

Incorporamos una API con varios modulos de tolerancia a fallas, volviendola una app robusta y dificil de romper anet fallos o accidentes de usuario. Ademas tiene integrado y configurado 
un [Circuit Breaker](https://docs.spring.io/spring-cloud-circuitbreaker/docs/current/reference/html/spring-cloud-circuitbreaker-resilience4j.html). Este bean pasa por tres estados principales:
- Cerrado: Estado por defecto. Permite que los servicios esten accesibles al usuario, mientras no se cruce un umbral de fallas (se peude definir un porcentaje de request con algun fallo
tratado por cada tantas resuest) permitiendo cambiar de estado.
- Abierto: Tras cruzar el umbral, se abre el C. B. bloqueando (de manera controlada y avisando al usuario) que uno o varios servicios de la API se encuentran en recuperación, durante un 
lapso de tiempo finito (por ejemplo: 10 o 20 segundos) 
- Medio-abierto: Pasado el tiempo de bloqueo, se permite el acceso a los servicios con request de prueba, si un porcentaje de estas request es exitosa el C. B.  vuelve a su estado cerrado.

Todo el comportamiento anterior puede ser configurado, desde cantidad de request en cada estado del C. B. lapsos de tiempo y porcentajes, hasta la acción de un Fall Back, que tomara el 
flujo de trabajo en una dirección que proteja la información recibida o simplemente notifique al usuario.

```java
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

```
<br>

### Navegación por menú
[Video de pantallas de app](https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/9f2664f8a53258a640bd8a3bb6981df105247446/videos/PantallasApp.mp4)

<br>

### Resiliencia desde App movil
https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/main/videos/ResilienciaApp.mp4

<br>

### Resiliencia en API desde Insomnia
https://github.com/CristopherLodbrok117/api-circuit-breaker-resilience/blob/main/videos/ResilienciaApp.mp4

