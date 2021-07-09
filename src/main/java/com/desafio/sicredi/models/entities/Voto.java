package com.desafio.sicredi.models.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.desafio.sicredi.models.enums.TipoVoto;

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
@Table(name = "VOTOS")
public class Voto {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "ID")
     private Long id;

     @NotNull
     @Column(name = "TIPO_VOTO")
     private TipoVoto tipoVoto;

     @ManyToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "ID_SESSAO", referencedColumnName="Id")
     private Sessao sessao;

     @ManyToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "ID_PAUTA")
     private Pauta pauta;

     @OneToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "ID_ASSOCIADO")
     private Associado associado;

}
