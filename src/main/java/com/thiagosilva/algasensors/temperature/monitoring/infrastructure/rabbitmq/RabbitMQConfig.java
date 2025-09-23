package com.thiagosilva.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Este é um serviço consumidor. por isso, não será configurado o bean de uma exchange
 * porque senão este consumidor também estaria configurando uma exchange junto com o
 * temperature-processing, sendo que ele apenas deve consumir a fila aqui é feito apenas
 * o binding da queue que é criada nesta config
*/
@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "temperature-monitoring.process-temperature.v1.q";
    public static final String FANOUT_EXCHANGE_NAME = "temperature-processing.temperature-received.v1.e";

    /*
     * Carrega o bean do jackson para dentro do rabbitmq deste microserviço
     * fazendo com que os payloads complexos que forem publicados ou lidos
     * aqui sejam serializados para json automaticamente
    */
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
        return new RabbitAdmin(factory);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
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
                FANOUT_EXCHANGE_NAME).build();
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
