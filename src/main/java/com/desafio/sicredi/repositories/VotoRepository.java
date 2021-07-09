package com.desafio.sicredi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.sicredi.models.entities.Pauta;
import com.desafio.sicredi.models.entities.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {

     List<Voto> findByPauta(Pauta pauta);
}
