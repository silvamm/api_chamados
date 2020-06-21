package br.rec.alpha.apichamados.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.rec.alpha.apichamados.model.Problema;
import br.rec.alpha.apichamados.repository.ProblemaRepository;

@Service
public class ProblemaService {
	
	@Autowired
	private ProblemaRepository repository;
	
	public List<Problema> listAll(){
		
		Problema modelo = new Problema();
		modelo.setAtivo(true);
		
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withMatcher("ativo", exact());
				
		Example<Problema> example = Example.of(modelo, matcher);
		
		return repository.findAll(example, Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<Problema> findById(Long id) {
		return repository.findById(id);
	}
	
	public Problema save(Problema problema) {
		problema.setAtivo(true);
		return repository.save(problema);
	}
	
	public Optional<Problema> edit(Problema problema) {
		return findById(problema.getId()).map(registro -> {
			registro.setNome(problema.getNome());
			registro = repository.save(problema);
			return Optional.ofNullable(registro);
		}).orElseGet(() -> Optional.empty());
	}
	
	public void delete(Long id) {
		repository.findById(id).ifPresent(registro -> {
			registro.setAtivo(false);
			repository.save(registro);
		});
	}

}
