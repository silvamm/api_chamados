package br.rec.alpha.apichamados.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

import br.rec.alpha.apichamados.enumm.TipoUsuarioEnum;
import lombok.Data;

@Data
@Entity
public class Usuario implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8514785501908999792L;
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
