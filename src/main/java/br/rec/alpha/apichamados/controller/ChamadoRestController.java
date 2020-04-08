package br.rec.alpha.apichamados.controller;

import java.util.List;

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

import br.rec.alpha.apichamados.model.Chamado;
import br.rec.alpha.apichamados.service.ChamadoService;

@RestController
@RequestMapping("/chamado")
public class ChamadoRestController {
	
	@Autowired
	public ChamadoService service;
	
	@GetMapping("/")
	public List<Chamado> listar(){
		return service.listAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Chamado> get(@PathVariable Long id) {
		return ResponseEntity.of(service.findById(id));
	}
	
	@PostMapping("/")
	public Chamado criar(@RequestBody Chamado chamado) {
		return service.save(chamado);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Chamado> editar(@PathVariable Long id, @RequestBody Chamado chamado){
		return service.findById(id)
				.map(registro -> {
					chamado.setId(registro.getId());
					Chamado atualizado = service.save(chamado);
					return ResponseEntity.ok(atualizado);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

}
