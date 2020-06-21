package br.rec.alpha.apichamados.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloHomeRestController {
	
	@GetMapping("/")
	public String helloHome() {
		return "Essa é a melhor API que você já consumiu!";
	}

}
