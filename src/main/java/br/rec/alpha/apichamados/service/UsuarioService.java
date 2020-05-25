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

	public void delete(Long id) {
		repo.deleteById(id);
	}

	public List<Usuario> listAll() {
		return repo.findAll();
	}

	public Optional<Usuario> findByEmail(String email) {
		return repo.findByEmail(email);
	}

	public Optional<Usuario> findById(Long id) {
		return repo.findById(id);
	}

	public Usuario save(Usuario usuario) {

		if (usuario.getId() != null)
			return edit(usuario);

		if (emailJaCadastrado(usuario.getEmail()))
			throw new IllegalArgumentException("O e-mail já esta cadastrado");

		if (usuario.getSenha() == null || usuario.getSenha().isBlank())
			throw new IllegalArgumentException("A senha está em branco");

		String encrpyt = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
		usuario.setSenha(encrpyt);

		return repo.save(usuario);

	}

	private Usuario edit(Usuario usuario) {

		Usuario usuarioDb = findById(usuario.getId())
				.orElseThrow(() -> new IllegalArgumentException("O usuário não foi encontrado"));

		if (!usuario.getEmail().equals(usuarioDb.getEmail()))
			if (emailJaCadastrado(usuario.getEmail()))
				throw new IllegalArgumentException("O e-mail já esta cadastrado");

		if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
			String encrpyt = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
			usuario.setSenha(encrpyt);
		} else {
			usuario.setSenha(usuarioDb.getSenha());
		}

		return repo.save(usuario);

	}

	public boolean emailJaCadastrado(String email) {
		return repo.existsByEmail(email);
	}

}
