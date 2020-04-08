package br.rec.alpha.apichamados.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rec.alpha.apichamados.model.ChamadoPredefinido;
import br.rec.alpha.apichamados.repository.ChamadoPredefinidoRepository;

@Service
public class ChamadoPredefinidosService {
	
	@Autowired
	private ChamadoPredefinidoRepository repo;
	
	public List<ChamadoPredefinido> listAll(){
		return repo.findAll();
	}
	
	public Optional<ChamadoPredefinido> findById(Long id) {
		return repo.findById(id);
	}
	
	public ChamadoPredefinido save(ChamadoPredefinido definicao) {
		return repo.save(definicao);
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}
