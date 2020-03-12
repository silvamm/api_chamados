package br.rec.alpha.apichamados.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rec.alpha.apichamados.model.Usuario;
import br.rec.alpha.apichamados.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> listAll(){
		return usuarioRepository.findAll();
	}
	
	public Usuario findById(Long idUsuario) {
		return usuarioRepository.getOne(idUsuario);
	}
	
	public void save(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	

}
