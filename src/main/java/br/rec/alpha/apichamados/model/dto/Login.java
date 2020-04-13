package br.rec.alpha.apichamados.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Login implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3838793839479849377L;
	private String email;
	private String senha;
	
}
