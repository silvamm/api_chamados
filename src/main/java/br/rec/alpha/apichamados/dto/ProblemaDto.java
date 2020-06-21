package br.rec.alpha.apichamados.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.rec.alpha.apichamados.model.Problema;
import lombok.Data;

@Data
public class ProblemaDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	public ProblemaDto(Problema problema) {
		this.id = problema.getId();
		this.nome = problema.getNome();
	}

}
