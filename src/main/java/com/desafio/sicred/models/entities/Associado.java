package com.desafio.sicred.models.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.desafio.sicred.models.enums.StatusVotacao;

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
@Table(name = "ASSOCIADOS")
public class Associado implements Serializable {

     private static final long serialVersionUID = -6653887982570783374L;

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "ID")
     private Long id;

     @NotNull
     @Column(name = "NOME")
     private String nome;

     @NotNull
     @Column(name = "CPF")
     private String cpf;

     @Column(name = "STATUS_VOTACAO")
     private StatusVotacao statusVotacao;

     @ManyToOne
     @JoinColumn(name = "VOTO_ID")
     private Voto voto;

}
