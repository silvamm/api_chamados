package br.rec.alpha.apichamados.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rec.alpha.apichamados.dto.LoginDto;
import br.rec.alpha.apichamados.model.Usuario;

@Service
public class LoginService {

	@Autowired
	private UsuarioService usuarioService;

	public Optional<Usuario> login(LoginDto login) {
		
		Optional<Usuario> optUsuario = usuarioService.findByEmail(login.getEmail());
		
		if(optUsuario.isPresent()) {
			
			Usuario usuario = optUsuario.get();
			boolean senhaCorreta = BCrypt.checkpw(login.getSenha(), usuario.getSenha()); 
			
			if (senhaCorreta) 
				return Optional.of(usuario);
			
		}
		
		return Optional.empty();
	}

}
