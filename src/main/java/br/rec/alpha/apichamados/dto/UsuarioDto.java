package br.rec.alpha.apichamados.dto;


import br.rec.alpha.apichamados.model.Setor;
import br.rec.alpha.apichamados.model.Usuario;
import lombok.Data;

@Data
public class UsuarioDto {
	
	private Long id;
	private String email;
	private String nome;
	private Setor setor;
	private String tipo;
	
	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.email = usuario.getEmail();
		this.nome = usuario.getNome();
		this.setor = usuario.getSetor();
		this.tipo = usuario.getTipo() != null ? usuario.getTipo().getNome() : null;
	}

}
