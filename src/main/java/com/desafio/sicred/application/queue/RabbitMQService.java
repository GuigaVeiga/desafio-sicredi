package com.desafio.sicred.application.queue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RabbitMQService {

	private final AmqpTemplate rabbitTemplate;

	public void send(String nomeFila, Object mensagem) {
		rabbitTemplate.convertAndSend(nomeFila, mensagem);
	}
}
