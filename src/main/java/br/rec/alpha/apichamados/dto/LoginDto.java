package br.rec.alpha.apichamados.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3838793839479849377L;
	private String email;
	private String senha;
	
}
