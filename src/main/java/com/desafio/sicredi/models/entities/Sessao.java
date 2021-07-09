package com.desafio.sicredi.models.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
public class Sessao {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "ID")
     private Long id;

     @Column(name = "DURACAO")
     private Long duracao;

     @CreatedDate
     @Column(name = "ABERTURA")
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime abertura;

     @NotNull
     @Column(name = "FECHAMENTO")
     @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
     private LocalDateTime fechamento;

     @NotNull
     @Column(name = "ATIVO")
     private boolean ativo;

     @OneToOne(mappedBy = "sessao")
     @JoinColumn(name = "ID_PAUTA")
     private Pauta pauta;

     @OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL)
     private List<Voto> votos;
}
