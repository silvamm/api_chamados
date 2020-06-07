package br.rec.alpha.apichamados.enumm;

public enum StatusChamadoEnum {
	
	PENDENTE("Pendente"),
	VISUALIZADO("Visualizado"),
	ACEITO("Aceito"),
	CANCELADO("Cancelado"),
	ENCERRADO("Encerrado");
	
	private final String nome;

	private StatusChamadoEnum(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
