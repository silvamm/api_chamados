package br.rec.alpha.apichamados.enumm;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoUsuarioEnum {
	
	COMUM("Comum"),
	ADMINISTRADOR("Administrador");
	
	private final String nome;
	
	private TipoUsuarioEnum(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	@JsonCreator
	public static TipoUsuarioEnum fromNome(String nome) {
		for (TipoUsuarioEnum tipo : TipoUsuarioEnum.values()) {
			if (tipo.getNome().equals(nome)) 
				return tipo;
		}
		TipoUsuarioEnum tipo = TipoUsuarioEnum.valueOf(nome);
		if(tipo != null) return tipo;
		throw new IllegalArgumentException();
	}

	
}
