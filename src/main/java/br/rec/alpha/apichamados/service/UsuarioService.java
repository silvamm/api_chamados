package br.rec.alpha.apichamados.service;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
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
	
	public Optional<Usuario> findByEmail(String email){
		return repo.findByEmail(email);
	}
	
	public Optional<Usuario> findById(Long id) {
		return repo.findById(id);
	}
	
	public Usuario save(Usuario usuario) {
		
		if(repo.findByEmail(usuario.getEmail()).isPresent()) 
			throw new IllegalArgumentException("O e-mail já esta cadastrado");
		
		String encrpyt = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
		usuario.setSenha(encrpyt);
		return repo.save(usuario);
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}
