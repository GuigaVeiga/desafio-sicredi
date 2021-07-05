package com.desafio.sicred.models.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SESSOES")
public class Sessao {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "ID")
     private Long id;

     @NotNull
     @Column(name = "TEMPO")
     private Integer tempo;

     @NotNull
     @Column(name = "ABERTURA")
     @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
     private LocalDateTime abertura = LocalDateTime.now();

     @NotNull
     @Column(name = "FECHAMENTO")
     @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
     private LocalDateTime fechamento;

     @NotNull
     @Column(name = "ATIVO")
     private boolean ativo;

     @OneToOne(mappedBy = "sessao")
     @JoinColumn(name = "PAUTA_ID")
     private Pauta pauta;

     @OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL)
     private List<Voto> votos;
}
