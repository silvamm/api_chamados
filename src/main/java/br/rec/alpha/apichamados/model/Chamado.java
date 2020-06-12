package br.rec.alpha.apichamados.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.rec.alpha.apichamados.enumm.PrioridadeChamadoEnum;
import br.rec.alpha.apichamados.enumm.StatusChamadoEnum;
import lombok.Data;

@Data
@Entity
public class Chamado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String protocolo;
	@ManyToOne
	private Problema problema;
	private String descricao;
	private StatusChamadoEnum status;
	private PrioridadeChamadoEnum prioridade;
	private LocalDateTime criadoEm;
	private LocalDateTime encerradoEm;
	@ManyToOne
	private Usuario criadoPor;

}
