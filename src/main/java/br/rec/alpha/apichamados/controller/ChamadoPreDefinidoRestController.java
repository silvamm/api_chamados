package br.rec.alpha.apichamados.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.rec.alpha.apichamados.model.ChamadoPreDefinido;
import br.rec.alpha.apichamados.service.ChamadoPreDefinidosService;

@RestController
@RequestMapping("/chamadopredefinido")
public class ChamadoPreDefinidoRestController {
	
	@Autowired
	private ChamadoPreDefinidosService service;
	
	@GetMapping("/")
	public List<ChamadoPreDefinido> listar(){
		return service.listAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ChamadoPreDefinido> get(@PathVariable Long id) {
		return ResponseEntity.of(service.findById(id));
	}
	
	@PostMapping("/")
	public ChamadoPreDefinido criar(@RequestBody ChamadoPreDefinido definicao) {
		return service.save(definicao);
	}
	
	public ResponseEntity<ChamadoPreDefinido> editar(@PathVariable Long id, @RequestBody ChamadoPreDefinido definicao) {
		return service.findById(id)
				.map(registro -> {
					definicao.setId(registro.getId());
					ChamadoPreDefinido atualizado = service.save(definicao);
					return ResponseEntity.ok(atualizado);
				}).orElse(ResponseEntity.notFound().build());
		
	}
}
