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

import br.rec.alpha.apichamados.model.Setor;
import br.rec.alpha.apichamados.service.SetorService;

@RestController
@RequestMapping("/setor")
public class SetorRestController {
	
	@Autowired
	private SetorService service;
	
	@GetMapping("/")
	public List<Setor> listar(){
		return service.listAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Setor> get(@PathVariable Long id){
		return ResponseEntity.of(service.findById(id));
	}

	@PostMapping("/")
	public Setor criar(@RequestBody Setor setor) {
		return service.save(setor);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Setor> editar(@PathVariable Long id, @RequestBody Setor setor ){
		return service.edit(setor)
				.map(editado -> {
					return ResponseEntity.ok(editado);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
