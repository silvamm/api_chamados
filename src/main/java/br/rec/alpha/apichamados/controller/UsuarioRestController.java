package br.rec.alpha.apichamados.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.rec.alpha.apichamados.model.Usuario;
import br.rec.alpha.apichamados.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/")
	public List<Usuario> listar() {
		return service.listAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> get(@PathVariable Long id) {
		Optional<Usuario> usuario = service.findById(id);
		return ResponseEntity.of(usuario);
	}
	
	@PostMapping("/")
	public Usuario salvar(@RequestBody Usuario usuario){
		return service.save(usuario); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> editar(@PathVariable Long id, @RequestBody Usuario usuario) {
		return service.findById(id)
		           .map(registro -> {
		        	   usuario.setId(registro.getId());
		               Usuario atualizado = service.save(usuario);
		               return ResponseEntity.ok(atualizado);
		           }).orElse(ResponseEntity.notFound().build());
		
	}

}
