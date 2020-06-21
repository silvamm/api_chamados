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

import br.rec.alpha.apichamados.dto.ProblemaDto;
import br.rec.alpha.apichamados.model.Problema;
import br.rec.alpha.apichamados.service.ProblemaService;

@RestController
@RequestMapping("/problema") 
public class ProblemaRestController {
	
	@Autowired
	private ProblemaService service;
	
	@GetMapping("/")
	public List<ProblemaDto> listar(){
		return service.listAll().stream().map(ProblemaDto::new).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProblemaDto> get(@PathVariable Long id) {
		return service.findById(id)
				.map(registro -> {
					ProblemaDto problemaDto = new ProblemaDto(registro);
					return ResponseEntity.ok(problemaDto);
				}).orElse(ResponseEntity.notFound().build());
		 
	}
	
	@PostMapping("/")
	public ProblemaDto criar(@RequestBody Problema problema) {
		Problema salvo = service.save(problema);
		ProblemaDto problemaDto = new ProblemaDto(salvo);
		return problemaDto;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProblemaDto> editar(@PathVariable Long id, @RequestBody Problema problema) {
		return service.edit(problema)
				.map(editado -> {
					ProblemaDto editadoDto = new ProblemaDto(editado);
					return ResponseEntity.ok(editadoDto);
				}).orElse(ResponseEntity.notFound().build());
		
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
