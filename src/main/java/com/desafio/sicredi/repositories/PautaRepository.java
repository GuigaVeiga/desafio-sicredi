package com.desafio.sicredi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.sicredi.models.entities.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, Long> {

}
