package br.rec.alpha.apichamados.enumm;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusChamadoEnum {
	
	PENDENTE("Pendente"),
	VISUALIZADO("Visualizado"),
	ACEITO("Aceito"),
	ENCERRADO("Encerrado");
	
	private final String nome;

	private StatusChamadoEnum(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	@JsonCreator
	public static StatusChamadoEnum fromNome(String nome) {
		for (StatusChamadoEnum status : StatusChamadoEnum.values()) {
			if (status.getNome().equals(nome)) 
				return status;
		}
		throw new IllegalArgumentException();
	}

}
