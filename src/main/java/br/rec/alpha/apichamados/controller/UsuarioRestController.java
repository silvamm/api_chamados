package br.rec.alpha.apichamados.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.rec.alpha.apichamados.model.Usuario;
import br.rec.alpha.apichamados.service.UsuarioService;

@RestController
public class UsuarioRestController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(method = RequestMethod.GET, path = "/usuarios")
	public List<Usuario> listarChamados() {
		return usuarioService.listAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/usuario")
	public Usuario getUsuario(@RequestParam(value = "id") Long id) {
		return usuarioService.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, path ="/usuario")
	public void salvarUsuario(Usuario usuario){
		usuarioService.save(usuario); 
	}

}
