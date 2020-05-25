package br.rec.alpha.apichamados.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.rec.alpha.apichamados.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	
	public Optional<Usuario> findByEmail(String email);
	
	public boolean existsByEmail(String email);
}
