package com.desafio.sicredi.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.desafio.sicredi.models.enums.StatusPauta;

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
@Table(name = "PAUTAS")
@EntityListeners(AuditingEntityListener.class)
public class Pauta implements Serializable {

     private static final long serialVersionUID = -7696969648559782158L;

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "ID")
     private Long id;

     @NotNull
     @Column(name = "TITULO")
     private String titulo;

     @NotNull
     @Column(name = "DESCRICAO")
     private String descricao;

     @Column(name = "STATUS_PAUTA")
     private StatusPauta statusPauta = StatusPauta.EM_ANALISE;

     @OneToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "SESSAO_ID", referencedColumnName = "id")
     private Sessao sessao;

     @CreatedDate
     @Column(name = "CREATED_AT", nullable = false, updatable = false)
     private LocalDateTime createdAt;

     @LastModifiedDate
     @Column(name = "UPDATED_AT")
     private LocalDateTime updatedAt;
}
