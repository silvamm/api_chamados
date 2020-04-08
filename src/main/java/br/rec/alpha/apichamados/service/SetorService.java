package br.rec.alpha.apichamados.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rec.alpha.apichamados.model.Setor;
import br.rec.alpha.apichamados.repository.SetorRepository;

@Service
public class SetorService {
	
	@Autowired
	private SetorRepository repo;
	
	public List<Setor> listAll(){
		return repo.findAll();
	}
	
	public Optional<Setor> findById(Long id) {
		return repo.findById(id);
	}
	
	public Setor save(Setor setor) {
		return repo.save(setor);
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}
