package com.thiagosilva.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Este é um serviço consumidor. por isso, não será configurado o bean de uma exchange
 * porque senão este consumidor também estaria configurando uma exchange junto com o
 * temperature-processing, sendo que ele apenas deve consumir a fila aqui é feito apenas
 * o binding da queue que é criada nesta config
*/
@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
        return new RabbitAdmin(factory);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable("temperature-monitoring.process-temperature.v1.q").build();
    }

    /*
     * Como esse microsserviço será apenas um consumidor dessa exchange, então
     * não será adicionado o @Bean, pois não é necessário que o serviço que
     * apenas consome os dados também seja capaz de criar uma exchange. Isso
     * É apenas uma referência para a exchange para possibilitar a criação do
     * binding
     */
    public FanoutExchange exchange() {
        return ExchangeBuilder.fanoutExchange(
                "temperature-processing.temperature-received.v1.e").build();
    }

    /*
     * O Microsserviço de consumo mode ficar responsável por criar um Binding, pois
     */
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange());
    }

}
