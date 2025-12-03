package school.sptech.projetoMima.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Password Recovery
    @Value("${app.rabbitmq.password-recovery.queue}")
    private String passwordRecoveryQueue;

    @Value("${app.rabbitmq.password-recovery.exchange}")
    private String passwordRecoveryExchange;

    @Value("${app.rabbitmq.password-recovery.routing-key}")
    private String passwordRecoveryRoutingKey;

    // Comprovante Venda
    @Value("${app.rabbitmq.comprovante-venda.queue}")
    private String comprovanteVendaQueue;

    @Value("${app.rabbitmq.comprovante-venda.exchange}")
    private String comprovanteVendaExchange;

    @Value("${app.rabbitmq.comprovante-venda.routing-key}")
    private String comprovanteVendaRoutingKey;

    // Password Recovery Beans
    @Bean
    public Queue passwordRecoveryQueue() {
        return new Queue(passwordRecoveryQueue, true);
    }

    @Bean
    public DirectExchange passwordRecoveryExchange() {
        return new DirectExchange(passwordRecoveryExchange, true, false);
    }

    @Bean
    public Binding passwordRecoveryBinding() {
        return BindingBuilder
                .bind(passwordRecoveryQueue())
                .to(passwordRecoveryExchange())
                .with(passwordRecoveryRoutingKey);
    }

    // Comprovante Venda Beans
    @Bean
    public Queue comprovanteVendaQueue() {
        return new Queue(comprovanteVendaQueue, true);
    }

    @Bean
    public DirectExchange comprovanteVendaExchange() {
        return new DirectExchange(comprovanteVendaExchange, true, false);
    }

    @Bean
    public Binding comprovanteVendaBinding() {
        return BindingBuilder
                .bind(comprovanteVendaQueue())
                .to(comprovanteVendaExchange())
                .with(comprovanteVendaRoutingKey);
    }

    // Common Beans
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
