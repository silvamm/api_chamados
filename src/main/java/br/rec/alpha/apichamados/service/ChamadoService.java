package br.rec.alpha.apichamados.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rec.alpha.apichamados.model.Chamado;
import br.rec.alpha.apichamados.repository.ChamadoRepository;

@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository repo;
	
	public List<Chamado> listAll() {
		return repo.findAll();
	}
	
	public Optional<Chamado> findById(Long id) {
		return repo.findById(id);
	}
	
	public Chamado save(Chamado chamado) {
		return repo.save(chamado);
	}

}
