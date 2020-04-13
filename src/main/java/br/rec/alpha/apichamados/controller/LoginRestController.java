package br.rec.alpha.apichamados.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.rec.alpha.apichamados.model.Usuario;
import br.rec.alpha.apichamados.model.dto.Login;
import br.rec.alpha.apichamados.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginRestController {

	@Autowired
	private LoginService service;
	
	@PostMapping("/")
	public ResponseEntity<Usuario> login(@RequestBody Login login){
		Optional<Usuario> logado = service.login(login);
		return ResponseEntity.of(logado);
	}
	
}
