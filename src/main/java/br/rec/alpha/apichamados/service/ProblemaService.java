package br.rec.alpha.apichamados.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rec.alpha.apichamados.model.Problema;
import br.rec.alpha.apichamados.repository.ProblemaRepository;

@Service
public class ProblemaService {
	
	@Autowired
	private ProblemaRepository repo;
	
	public List<Problema> listAll(){
		return repo.findAll();
	}
	
	public Optional<Problema> findById(Long id) {
		return repo.findById(id);
	}
	
	public Problema save(Problema problema) {
		return repo.save(problema);
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}
