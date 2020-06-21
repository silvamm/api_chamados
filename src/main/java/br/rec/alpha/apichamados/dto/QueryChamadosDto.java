package br.rec.alpha.apichamados.dto;

import br.rec.alpha.apichamados.enumm.StatusChamadoEnum;
import lombok.Data;

@Data
public class QueryChamadosDto {
	
	private Long idUsuario;
	private String protocolo;
	private StatusChamadoEnum status;
	private Long idSetor;
	private int pagina;
	private int limite;

}
