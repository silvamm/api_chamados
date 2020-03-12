package br.rec.alpha.apichamados.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.rec.alpha.apichamados.enumm.TipoUsuarioEnum;
import lombok.Data;

@Entity
@Data
public class Usuario implements Serializable{
	
	 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String senha;
	private String nome;
	@ManyToOne
	private Setor setor;
	private TipoUsuarioEnum tipo;

}
