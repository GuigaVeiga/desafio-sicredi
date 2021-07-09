package com.desafio.sicredi.application.configuration;

import static com.desafio.sicredi.ConstantsApplication.FILA_RESULTADO_VOTACAO;
import static com.desafio.sicredi.ConstantsApplication.NOME_EXCHANGE_RESULTADO_VOTACAO;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMQConfig {

	private AmqpAdmin amqpAdmin;

	public RabbitMQConfig(AmqpAdmin amqpAdmin){
		this.amqpAdmin = amqpAdmin;
	}

	private Queue fila(String nomeFila){
		return new Queue(nomeFila, true, false, false);
	}

	private DirectExchange trocaDireta() {
		return new DirectExchange(NOME_EXCHANGE_RESULTADO_VOTACAO);
	}

	private Binding relacionamento(Queue fila, DirectExchange troca){
		return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
	}

	@PostConstruct
	private void criarFilas(){
		Queue filaResultado = this.fila(FILA_RESULTADO_VOTACAO);

		DirectExchange troca = this.trocaDireta();

		Binding binding = this.relacionamento(filaResultado, troca);

		//Criando as filas no RabbitMQ
		this.amqpAdmin.declareQueue(filaResultado);

		this.amqpAdmin.declareExchange(troca);

		this.amqpAdmin.declareBinding(binding);
	}
}
