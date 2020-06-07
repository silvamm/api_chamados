package br.rec.alpha.apichamados.dto;

import br.rec.alpha.apichamados.enumm.PrioridadeChamadoEnum;
import br.rec.alpha.apichamados.enumm.StatusChamadoEnum;
import lombok.Data;

@Data
public class QueryChamadosDto {
	
	private Long idUsuario;
	private String protocolo;
	private PrioridadeChamadoEnum prioridade;
	private StatusChamadoEnum status;
	private Long idSetor;
	
}
