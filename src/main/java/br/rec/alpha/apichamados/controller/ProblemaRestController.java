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

import br.rec.alpha.apichamados.model.Problema;
import br.rec.alpha.apichamados.service.ProblemaService;

@RestController
@RequestMapping("/problema") 
public class ProblemaRestController {
	
	@Autowired
	private ProblemaService service;
	
	@GetMapping("/")
	public List<Problema> listar(){
		return service.listAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Problema> get(@PathVariable Long id) {
		return ResponseEntity.of(service.findById(id));
	}
	
	@PostMapping("/")
	public Problema criar(@RequestBody Problema problema) {
		return service.save(problema);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Problema> editar(@PathVariable Long id, @RequestBody Problema problema) {
		return service.findById(id)
				.map(registro -> {
					problema.setId(registro.getId());
					Problema atualizado = service.save(problema);
					return ResponseEntity.ok(atualizado);
				}).orElse(ResponseEntity.notFound().build());
		
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
