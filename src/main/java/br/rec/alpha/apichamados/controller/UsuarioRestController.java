package br.rec.alpha.apichamados.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.rec.alpha.apichamados.dto.ChamadoDto;
import br.rec.alpha.apichamados.dto.QueryChamadosDto;
import br.rec.alpha.apichamados.dto.QueryUsuariosDto;
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
	
	@GetMapping("/query")
	public List<UsuarioDto> listar(@RequestBody QueryUsuariosDto query){
		return service.listAll(query).stream().map(UsuarioDto::new).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDto> get(@PathVariable Long id) {
		Optional<Usuario> encontrado = service.findById(id);
		Optional<UsuarioDto> usuarioDto = encontrado.isPresent() ? Optional.of(new UsuarioDto(encontrado.get())) : Optional.empty();
		return ResponseEntity.of(usuarioDto);
	}
	
	
	@PostMapping("/")
	public ResponseEntity<UsuarioDto> salvar(@RequestBody Usuario usuario){
		try {
			usuario.setId(null);
			Usuario salvo = service.save(usuario);
			UsuarioDto usuarioDto = new UsuarioDto(salvo);
			return ResponseEntity.ok(usuarioDto); 
		}catch(IllegalArgumentException e) {
			if(e.getMessage().contains("senha"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			 throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDto> editar(@PathVariable Long id, @RequestBody Usuario usuario) {
		return service.findById(id)
		           .map(registro -> {
		        	   usuario.setId(registro.getId());
		               Usuario atualizado = service.save(usuario);
		               UsuarioDto usuarioDto = new UsuarioDto(atualizado);
		               return ResponseEntity.ok(usuarioDto);
		           }).orElse(ResponseEntity.notFound().build());
		
	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		 service.delete(id);
	}

}
