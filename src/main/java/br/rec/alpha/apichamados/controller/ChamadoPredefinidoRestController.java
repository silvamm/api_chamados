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

import br.rec.alpha.apichamados.model.ChamadoPredefinido;
import br.rec.alpha.apichamados.service.ChamadoPredefinidosService;

@RestController
@RequestMapping("/chamadopredefinido")
public class ChamadoPredefinidoRestController {
	
	@Autowired
	private ChamadoPredefinidosService service;
	
	@GetMapping("/")
	public List<ChamadoPredefinido> listar(){
		return service.listAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ChamadoPredefinido> get(@PathVariable Long id) {
		return ResponseEntity.of(service.findById(id));
	}
	
	@PostMapping("/")
	public ChamadoPredefinido criar(@RequestBody ChamadoPredefinido definicao) {
		return service.save(definicao);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ChamadoPredefinido> editar(@PathVariable Long id, @RequestBody ChamadoPredefinido definicao) {
		return service.findById(id)
				.map(registro -> {
					definicao.setId(registro.getId());
					ChamadoPredefinido atualizado = service.save(definicao);
					return ResponseEntity.ok(atualizado);
				}).orElse(ResponseEntity.notFound().build());
		
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
