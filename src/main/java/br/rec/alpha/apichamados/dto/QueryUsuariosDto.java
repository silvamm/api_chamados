package br.rec.alpha.apichamados.dto;

import br.rec.alpha.apichamados.enumm.TipoUsuarioEnum;
import lombok.Data;

@Data
public class QueryUsuariosDto {
	
	private String nomeEmail;
	private TipoUsuarioEnum tipo;
	private Long idSetor;

}
