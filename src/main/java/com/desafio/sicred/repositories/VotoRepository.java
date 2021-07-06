package com.desafio.sicred.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.sicred.models.entities.Pauta;
import com.desafio.sicred.models.entities.Sessao;
import com.desafio.sicred.models.entities.Voto;
import com.desafio.sicred.models.enums.TipoVoto;

public interface VotoRepository extends JpaRepository<Voto, Long> {

     List<Voto> findByPauta(Pauta pauta);
}
