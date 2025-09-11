package com.thiagosilva.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RabbitMQInitializer {
    private final RabbitAdmin admin;

    /*
     * Faz esse método ser executado assim que a classe atual é criada,
     * e depois que as dependencias dela forem injetadas
     */
    @PostConstruct
    public void init() {

        /*
         * Cria as exchanges, queues e bindings que foram definidas
         * como beans em outro arquivo de configuração através do
         * RabbitMQConfig
         */
        admin.initialize();
    }
}
