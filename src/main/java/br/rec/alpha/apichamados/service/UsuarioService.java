package br.rec.alpha.apichamados.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rec.alpha.apichamados.model.Usuario;
import br.rec.alpha.apichamados.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repo;
	
	public List<Usuario> listAll(){
		return repo.findAll();
	}
	
	public Optional<Usuario> findById(Long id) {
		return repo.findById(id);
	}
	
	public Usuario save(Usuario usuario) {
		return repo.save(usuario);
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}
