package com.desafio.sicredi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.sicredi.models.entities.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

}
