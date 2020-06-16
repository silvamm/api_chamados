package br.rec.alpha.apichamados.dto;

import java.time.LocalDateTime;

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
	private String status;
	private LocalDateTime criadoEm;
	private LocalDateTime encerradoEm;
	private UsuarioDto criadoPor;
	
	
	public ChamadoDto(Chamado chamado) {
		this.id = chamado.getId();
		this.protocolo = chamado.getProtocolo();
		this.descricao = chamado.getDescricao();
		this.problema = chamado.getProblema();
		this.status = chamado.getStatus().getNome();
		this.criadoEm = chamado.getCriadoEm();
		this.encerradoEm = chamado.getEncerradoEm();
		this.criadoPor = new UsuarioDto(chamado.getCriadoPor());
	}

}
