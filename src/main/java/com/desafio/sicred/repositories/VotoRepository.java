package com.desafio.sicred.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.sicred.models.entities.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {

}
