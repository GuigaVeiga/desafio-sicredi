# Desafio-sicredi

## Objetivo
Criar uma solução back-end para gerenciar sessões de votação.

  - Cadastrar uma nova pauta;
  - Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo
    determinado na chamada de abertura ou 1 minuto por default);
  - Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é
    identificado por um id único e pode votar apenas uma vez por pauta);
  - Contabilizar os votos e dar o resultado da votação na pauta.

## Tecnologias Utilizadas
  - Java 1.8
  - Spring Boot
  - Postgres (RDS AWS)
  - RabbitMQ
  - Swagger
  - Maven
  - S3 (AWS)
  - EC2 (AWS)
  - Docker

## Como executar o projeto local
  - docker-compose up (Para subir o RabbitMQ)
  - rodar o projeto

## Link do projeto
  - Swagger: http://34.219.22.208:8080/swagger-ui.html#/
  - RabbitMQ: http://34.219.22.208:15672/

## Entidades
  - Associado
  - Pauta
  - Sessao
  - Voto

## Fluxo da Aplicação
- Criar associado: Adiciona associado com seu nome e cpf.
- Criar Pauta: Adiciona pauta com título e descrição
- Iniciar Sessão: Inicia uma sessão informando o ID da pauta, status e duração da sessão em minutos. Caso a duração não for preenchida, a sesão será de 1 minuto.
- Voto na Sessão: O associado realiza o voto na Pauta dentro da sessão, informando se vota SIM ou NÃO. Passando o Id do associado, sessão e pauta. Nesse momento também é validade se o associado está abilitado para votar por meio de um integraçção com um sistema externo.
- Fechar Sessão e Contabilizar os votos:  Informando o Id da sessão e pauta, a votação é encerrada e os votos são contabilizados. Nesse momento, o resultado é apresentado na tela e também enviados para fila no RabbitMQ. Mesmo que a sessão ainda esteja aberta, a mesma é fechada e desativada.
