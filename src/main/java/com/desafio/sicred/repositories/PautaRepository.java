package com.desafio.sicred.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.sicred.models.entities.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, Long> {

}