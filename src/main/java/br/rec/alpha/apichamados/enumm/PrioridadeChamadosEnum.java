package br.rec.alpha.apichamados.enumm;

public enum PrioridadeChamadosEnum {
	
	NORMAL("Normal"),
	URGENTE("Urgente"),
	CRITICO("Crítico");
	
	private final String nome;

	private PrioridadeChamadosEnum(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
