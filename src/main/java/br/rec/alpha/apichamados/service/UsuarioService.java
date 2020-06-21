package br.rec.alpha.apichamados.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import br.rec.alpha.apichamados.dto.QueryUsuariosDto;
import br.rec.alpha.apichamados.model.Setor;
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
		return repo.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public List<Usuario> listAll(QueryUsuariosDto query){
		
		List<Usuario> resultado = new ArrayList<>();
		
		Setor setor = new Setor();
		setor.setId(query.getIdSetor());
		
		Usuario usuario = new Usuario();
		usuario.setSetor(setor);
		usuario.setTipo(query.getTipo());
		usuario.setNome(query.getNomeEmail());
		
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withMatcher("setor.id", exact())
				.withMatcher("nome", contains()).withIgnoreCase()
				.withMatcher("tipo", exact());
		
		Example<Usuario> example = Example.of(usuario, matcher);
		resultado.addAll(repo.findAll(example));
		
		usuario.setNome(null);
		usuario.setEmail(query.getNomeEmail());
		matcher = ExampleMatcher
				.matching()
				.withMatcher("setor.id", exact())
				.withMatcher("email", contains()).withIgnoreCase()
				.withMatcher("tipo", exact());
		
		example = Example.of(usuario, matcher);
		
		List<Usuario> usuarios = repo.findAll(example)
				.stream()
				.filter(u -> !resultado.contains(u))
				.collect(Collectors.toList());
		
		resultado.addAll(usuarios);
		resultado.sort(Comparator.comparing(Usuario::getId));
		return resultado;
	}

	public Optional<Usuario> findByEmail(String email) {
		return repo.findByEmail(email);
	}

	public Optional<Usuario> findById(Long id) {
		return repo.findById(id);
	}

	public Usuario save(Usuario usuario) {

		if (usuario.getId() != null)
			throw new IllegalArgumentException("O usuário tem id. Use o método edit");

		if (emailJaCadastrado(usuario.getEmail()))
			throw new IllegalArgumentException("O e-mail já esta cadastrado");

		if (usuario.getSenha() == null || usuario.getSenha().isBlank())
			throw new IllegalArgumentException("A senha está em branco");

		String encrpyt = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
		usuario.setSenha(encrpyt);

		return repo.save(usuario);

	}

	public Optional<Usuario> edit(Usuario usuario) {
		
		return findById(usuario.getId()).map(registro -> {
			
			if (!usuario.getEmail().equals(registro.getEmail()))
				if (emailJaCadastrado(usuario.getEmail()))
					throw new IllegalArgumentException("O e-mail já esta cadastrado");

			if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
				String encrpyt = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
				usuario.setSenha(encrpyt);
			} else {
				usuario.setSenha(registro.getSenha());
			}
			
			registro = repo.save(usuario);
			return Optional.ofNullable(registro);
			
		}).orElseGet(() -> Optional.empty());

	}

	public boolean emailJaCadastrado(String email) {
		return repo.existsByEmail(email);
	}

}
