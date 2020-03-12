package br.rec.alpha.apichamados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rec.alpha.apichamados.repository.SetorRepository;

@Service
public class SetorService {
	
	@Autowired
	private SetorRepository setorRepository;

}
