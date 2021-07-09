package com.desafio.sicredi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.sicredi.models.entities.Sessao;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

}
