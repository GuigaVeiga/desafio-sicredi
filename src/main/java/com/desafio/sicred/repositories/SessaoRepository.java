package com.desafio.sicred.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.sicred.models.entities.Sessao;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

}
