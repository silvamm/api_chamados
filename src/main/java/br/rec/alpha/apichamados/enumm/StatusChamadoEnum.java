package br.rec.alpha.apichamados.enumm;

public enum StatusChamadoEnum {
	
	PENDENTE("Pendente"),
	VISUALIZADO("Visualizado"),
	CANCELADO("Cancelado"),
	RESOLVIDO("Resolvido");
	
	private final String nome;

	private StatusChamadoEnum(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
