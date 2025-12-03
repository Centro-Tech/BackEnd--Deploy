package school.sptech.projetoMima.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.sptech.projetoMima.core.adapter.Usuario.PasswordRecoveryPublisherGateway;
import school.sptech.projetoMima.core.domain.Usuario;

import java.util.HashMap;
import java.util.Map;

@Component
public class PasswordRecoveryPublisherAdapter implements PasswordRecoveryPublisherGateway {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;
    private final String frontendResetUrl;

    public PasswordRecoveryPublisherAdapter(RabbitTemplate rabbitTemplate,
                                            @Value("${app.rabbitmq.password-recovery.exchange}") String exchange,
                                            @Value("${app.rabbitmq.password-recovery.routing-key}") String routingKey,
                                            @Value("${app.password-recovery.frontend-reset-url:http://localhost:3000/reset-password}") String frontendResetUrl) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.frontendResetUrl = frontendResetUrl;
    }

    @Override
    public void publishRecoveryInstructions(Usuario usuario, String token) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", usuario.getId());
        payload.put("email", usuario.getEmail());
        payload.put("nome", usuario.getNome());
        payload.put("token", token);
        payload.put("resetUrl", frontendResetUrl + "?token=" + token);
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
    }
}

