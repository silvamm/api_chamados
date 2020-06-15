package br.rec.alpha.apichamados.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.rec.alpha.apichamados.dto.ChamadoDto;
import br.rec.alpha.apichamados.dto.QueryChamadosDto;
import br.rec.alpha.apichamados.enumm.PrioridadeChamadoEnum;
import br.rec.alpha.apichamados.enumm.StatusChamadoEnum;
import br.rec.alpha.apichamados.model.Chamado;
import br.rec.alpha.apichamados.model.Setor;
import br.rec.alpha.apichamados.model.Usuario;
import br.rec.alpha.apichamados.repository.ChamadoRepository;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;

	public List<Chamado> listAll() {
		return repository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public List<Chamado> listAll(QueryChamadosDto query) {
		
		Usuario usuario = new Usuario();
		usuario.setId(query.getIdUsuario());
		Setor setor = new Setor();
		setor.setId(query.getIdSetor());
		usuario.setSetor(setor);
		
		Chamado chamado = new Chamado();
		chamado.setCriadoPor(usuario);
		chamado.setProtocolo(query.getProtocolo());
		chamado.setStatus(query.getStatus());
		
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withMatcher("criadoPor.setor.id", exact())
				.withMatcher("criadoPor.id", exact())
				.withMatcher("protocolo", contains())
				.withMatcher("status", exact());
		
		Example<Chamado> example = Example.of(chamado, matcher);
		
		return repository.findAll(example, Sort.by(Direction.DESC ,"id"));
	}

	public Optional<Chamado> findById(Long id) {
		return repository.findById(id);
	}

	public Chamado save(Chamado chamado) {
		
		LocalDateTime hoje = LocalDateTime.now();
		
		StringBuilder protocolo = new StringBuilder();
		protocolo
			.append(hoje.getYear())
			.append(hoje.getMonthValue())
			.append(hoje.getDayOfMonth())
			.append(hoje.getHour())
			.append(hoje.getMinute())
			.append(hoje.getSecond());
		
		Random random = new Random();
		String numeroRandom = String.valueOf(random.nextInt(50));
		
		protocolo.append(numeroRandom);
		
		chamado.setProtocolo(protocolo.toString());
		
		chamado.setCriadoEm(hoje);
		chamado.setStatus(StatusChamadoEnum.PENDENTE);
		chamado.setPrioridade(PrioridadeChamadoEnum.NORMAL);
		
		return repository.save(chamado);
	}
	
	public Optional<Chamado> edit(Chamado chamado) {
		
		return findById(chamado.getId())
			.map(registro -> {
				
				if(chamado.getEncerradoEm() == null 
						&& chamado.getStatus() == StatusChamadoEnum.ENCERRADO) {
					chamado.setEncerradoEm(LocalDateTime.now());
				}else {
					chamado.setEncerradoEm(registro.getEncerradoEm());
				}
				
				chamado.setCriadoEm(registro.getCriadoEm());
				chamado.setProtocolo(registro.getProtocolo());
				chamado.setCriadoPor(registro.getCriadoPor());
				
				return Optional.ofNullable(repository.save(chamado));
				
			}).orElseGet(() -> Optional.empty());
		
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

}
