package br.rec.alpha.apichamados.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.rec.alpha.apichamados.dto.LoginDto;
import br.rec.alpha.apichamados.dto.UsuarioDto;
import br.rec.alpha.apichamados.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginRestController {

	@Autowired
	private LoginService service;

	@PostMapping("/")
	public ResponseEntity<UsuarioDto> login(@RequestBody LoginDto login) {
		return service.login(login)
				.map(usuario -> {
					UsuarioDto dto = new UsuarioDto(usuario);
					return ResponseEntity.ok(dto);
				}).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
