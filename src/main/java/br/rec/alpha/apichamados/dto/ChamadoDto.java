package br.rec.alpha.apichamados.dto;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import br.rec.alpha.apichamados.enumm.PrioridadeChamadoEnum;
import br.rec.alpha.apichamados.enumm.StatusChamadoEnum;
import br.rec.alpha.apichamados.model.Chamado;
import br.rec.alpha.apichamados.model.Problema;
import lombok.Data;

@Data
public class ChamadoDto {
	
	private Long id;
	private String protocolo;
	private String descricao;
	private Problema problema;
	private StatusChamadoEnum status;
	private PrioridadeChamadoEnum prioridade;
	private Long criadoEm;
	private Long encerradoEm;
	private UsuarioDto criadoPor;
	
	
	public ChamadoDto(Chamado chamado) {
		
		this.id = chamado.getId();
		this.protocolo = chamado.getProtocolo();
		this.descricao = chamado.getDescricao();
		this.problema = chamado.getProblema();
		this.status = chamado.getStatus();
		this.prioridade = chamado.getPrioridade();
		
		ZonedDateTime zdt = ZonedDateTime.of(chamado.getCriadoEm(), ZoneId.systemDefault());
		this.criadoEm = zdt.toInstant().toEpochMilli();
		
		if(chamado.getEncerradoEm() != null) {
			zdt = ZonedDateTime.of(chamado.getEncerradoEm(), ZoneId.systemDefault());
			this.encerradoEm = zdt.toInstant().toEpochMilli();
		}
		
		this.criadoPor = new UsuarioDto(chamado.getCriadoPor());
	}

}
