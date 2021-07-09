package com.desafio.sicredi;

import org.springframework.stereotype.Component;

@Component
public interface ConstantsApplication {

	String NOME_EXCHANGE_RESULTADO_VOTACAO = "amq.direct";
	String FILA_RESULTADO_VOTACAO = "RESULTADO";


}
