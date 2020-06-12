package br.rec.alpha.apichamados.controller;

import java.util.List;
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

import br.rec.alpha.apichamados.dto.ChamadoDto;
import br.rec.alpha.apichamados.dto.QueryChamadosDto;
import br.rec.alpha.apichamados.model.Chamado;
import br.rec.alpha.apichamados.service.ChamadoService;

@RestController
@RequestMapping("/chamado")
public class ChamadoRestController {
	
	@Autowired
	public ChamadoService service;
	
	@GetMapping("/")
	public List<ChamadoDto> listar(){
		return service.listAll().stream().map(ChamadoDto::new).collect(Collectors.toList());
	}
	
	@GetMapping("/query")
	public List<ChamadoDto> listar(@RequestBody QueryChamadosDto query){
		return service.listAll(query).stream().map(ChamadoDto::new).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ChamadoDto> get(@PathVariable Long id) {
		return service.findById(id)
				.map(registro -> {
					ChamadoDto chamadoDto = new ChamadoDto(registro);
					return ResponseEntity.ok(chamadoDto);
				}).orElse(ResponseEntity.notFound().build());
		
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
