package br.rec.alpha.apichamados.enumm;

public enum PrioridadeChamadoEnum {
	
	NORMAL("Normal"),
	URGENTE("Urgente"),
	CRITICO("Crítico");
	
	private final String nome;

	private PrioridadeChamadoEnum(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
