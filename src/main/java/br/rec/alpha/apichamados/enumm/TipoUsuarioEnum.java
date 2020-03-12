package br.rec.alpha.apichamados.enumm;

public enum TipoUsuarioEnum {
	
	NORMAL("Normal"),
	ADMINISTRADOR("Administrador");
	
	private final String nome;
	
	private TipoUsuarioEnum(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
}
