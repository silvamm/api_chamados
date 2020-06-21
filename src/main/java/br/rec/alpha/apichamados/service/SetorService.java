package br.rec.alpha.apichamados.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.rec.alpha.apichamados.model.Setor;
import br.rec.alpha.apichamados.repository.SetorRepository;

@Service
public class SetorService {
	
	@Autowired
	private SetorRepository repository;
	
	public List<Setor> listAll(){
		return repository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<Setor> findById(Long id) {
		return repository.findById(id);
	}
	
	public Setor save(Setor setor) {
		return repository.save(setor);
	}
	
	public Optional<Setor> edit(Setor setor) {
		return findById(setor.getId()).map(registro -> {
			registro.setNome(setor.getNome());
			registro = repository.save(setor);
			return Optional.ofNullable(registro);
		}).orElseGet(() -> Optional.empty());
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
