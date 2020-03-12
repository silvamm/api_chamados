package br.rec.alpha.apichamados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.rec.alpha.apichamados.model.Setor;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {

}
