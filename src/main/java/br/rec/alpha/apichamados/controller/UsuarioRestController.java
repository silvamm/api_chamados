package br.rec.alpha.apichamados.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.rec.alpha.apichamados.dto.UsuarioDto;
import br.rec.alpha.apichamados.model.Usuario;
import br.rec.alpha.apichamados.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/")
	public List<UsuarioDto> listar() {
		return service.listAll().stream().map(UsuarioDto::new).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDto> get(@PathVariable Long id) {
		Optional<Usuario> encontrado = service.findById(id);
		return ResponseEntity.of(encontrado.isPresent() ? Optional.of(new UsuarioDto(encontrado.get())) : Optional.empty());
	}
	
	
	@PostMapping("/")
	public UsuarioDto salvar(@RequestBody Usuario usuario){
		Usuario salvo = service.save(usuario);
		return new UsuarioDto(salvo); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDto> editar(@PathVariable Long id, @RequestBody Usuario usuario) {
		return service.findById(id)
		           .map(registro -> {
		        	   usuario.setId(registro.getId());
		               Usuario atualizado = service.save(usuario);
		               return ResponseEntity.ok(new UsuarioDto(atualizado));
		           }).orElse(ResponseEntity.notFound().build());
		
	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		 service.delete(id);
	}

}
