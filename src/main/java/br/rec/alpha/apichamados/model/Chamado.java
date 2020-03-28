package br.rec.alpha.apichamados.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.rec.alpha.apichamados.enumm.PrioridadeChamadosEnum;
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
	private ChamadoPreDefinido preDefinida;
	private String descricao;
	private StatusChamadoEnum status;
	private PrioridadeChamadosEnum prioridade;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataEnceramento;
	@ManyToOne
	private Usuario criadoPor;

}
