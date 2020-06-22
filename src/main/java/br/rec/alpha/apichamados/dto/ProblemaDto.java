package br.rec.alpha.apichamados.dto;

import br.rec.alpha.apichamados.model.Problema;
import lombok.Data;

@Data
public class ProblemaDto {
	
	private Long id;
	private String nome;
	
	public ProblemaDto(Problema problema) {
		this.id = problema.getId();
		this.nome = problema.getNome();
	}

}
