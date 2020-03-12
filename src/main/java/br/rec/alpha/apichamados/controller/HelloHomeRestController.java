package br.rec.alpha.apichamados.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloHomeRestController {
	
	@RequestMapping(method = RequestMethod.GET, path = "/")
	public String helloHome() {
		return "Essa é a melhor API da Faculdade Alpha!";
	}

}
