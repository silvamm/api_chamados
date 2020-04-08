package br.rec.alpha.apichamados.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rec.alpha.apichamados.model.ChamadoPreDefinido;
import br.rec.alpha.apichamados.repository.ChamadoPreDefinidoRepository;

@Service
public class ChamadoPreDefinidosService {
	
	@Autowired
	private ChamadoPreDefinidoRepository repo;
	
	public List<ChamadoPreDefinido> listAll(){
		return repo.findAll();
	}
	
	public Optional<ChamadoPreDefinido> findById(Long id) {
		return repo.findById(id);
	}
	
	public ChamadoPreDefinido save(ChamadoPreDefinido definicao) {
		return repo.save(definicao);
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}
