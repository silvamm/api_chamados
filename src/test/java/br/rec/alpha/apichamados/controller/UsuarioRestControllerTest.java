package br.rec.alpha.apichamados.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;

import br.rec.alpha.apichamados.enumm.TipoUsuarioEnum;
import br.rec.alpha.apichamados.model.Setor;
import br.rec.alpha.apichamados.model.Usuario;
import br.rec.alpha.apichamados.repository.UsuarioRepository;
import br.rec.alpha.apichamados.service.UsuarioService;

@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
public class UsuarioRestControllerTest {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@MockBean
	private UsuarioService service;
	
	private Usuario usuario;

	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(restDocumentation).uris()
		                .withScheme("https")
		                .withHost("softrec.com.br")
		                .withPort(8080))
				.build();
		
	 	usuario = new Usuario();
    	usuario.setId(1L);
    	usuario.setNome("Teste");
    	usuario.setSenha("senha");
    	usuario.setEmail("teste@email.com.br");
    	usuario.setTipo(TipoUsuarioEnum.ADMINISTRADOR);
    	
    	Setor setor = new Setor();
    	setor.setId(1L);
    	setor.setNome("Teste");
    	
    	usuario.setSetor(setor);
    	
		given(service.findById(usuario.getId())).willReturn(Optional.of(usuario));
	}
	
	private FieldDescriptor[] getDescricaoDosAtributosDoUsuario() {
	    return new FieldDescriptor[]{fieldWithPath("id").description("O identificador único do usuário").type(JsonFieldType.NUMBER),
	                fieldWithPath("email").description("O e-mail do usuário").type(JsonFieldType.STRING),
	                fieldWithPath("senha").description("A senha do usuário. Não é retornada").optional().type(JsonFieldType.STRING),
	                fieldWithPath("nome").description("O nome do usuário").type(JsonFieldType.STRING),
	                subsectionWithPath("setor").description("O setor que o usuário faz parte").optional().type(JsonFieldType.OBJECT),
	                fieldWithPath("tipo").description("O tipo de usuário (ADMINISTRADOR ou NORMAL)").optional().type(JsonFieldType.STRING)
	    };
	}
	
	private FieldDescriptor[] getDescricaoDosAtributosDeUmaListaDeUsuarios() {
	    return new FieldDescriptor[]{
	    			fieldWithPath("[]").description("Lista de usuários").type(JsonFieldType.ARRAY),
	    			subsectionWithPath("[].id").description("O identificador único do usuário").type(JsonFieldType.NUMBER),
	    			subsectionWithPath("[].email").description("O e-mail do usuário").type(JsonFieldType.STRING),
	                subsectionWithPath("[].senha").description("A senha do usuário. Não é retornada").optional().type(JsonFieldType.STRING),
	                subsectionWithPath("[].nome").description("O nome do usuário").type(JsonFieldType.STRING),
	                subsectionWithPath("[].setor").description("O setor que o usuário faz parte").optional().type(JsonFieldType.OBJECT),
	                subsectionWithPath("[].tipo").description("O tipo de usuário (ADMINISTRADOR ou NORMAL)").optional().type(JsonFieldType.STRING)
	    };
	}
	
	@Test
	public void list() throws Exception {

		Setor setor2 = new Setor();
		setor2.setId(2L);
		setor2.setNome("Teste 2");
		
		Usuario usuario2 = new Usuario();
		usuario2.setId(2L);
		usuario2.setNome("Teste 2");
		usuario2.setSenha("senha2");
		usuario2.setEmail("teste2@alpha.com.br");
		usuario2.setTipo(TipoUsuarioEnum.NORMAL);
		usuario2.setSetor(setor2);
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		usuarios.add(usuario2);
		
		given(service.listAll()).willReturn(usuarios);
		
	   mockMvc.perform(
    		RestDocumentationRequestBuilders
                .get("/usuario/"))
        		.andExpect(status().isOk())
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("usuario/list", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint()),
	    				responseFields(getDescricaoDosAtributosDeUmaListaDeUsuarios())));
		
	}
	
    @Test
    public void update() throws Exception {
    	
    	usuario.setNome("Teste Atualizado");
    	
		given(service.save(usuario)).willReturn(usuario);

        mockMvc.perform(
    		RestDocumentationRequestBuilders
                .put("/usuario/{id}", 1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(usuario)))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.nome", is("Teste Atualizado")))
				.andExpect(jsonPath("$.senha").doesNotExist())
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("usuario/update", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint())));
        
    }
	
	@Test
	public void create() throws JsonProcessingException, Exception {
		
		Setor setor = new Setor();
		setor.setId(1L);
		
		Usuario novoUsuario = new Usuario();
		novoUsuario.setNome("Teste");
		novoUsuario.setEmail("teste@alpha.com.br");
		novoUsuario.setSenha("senha");
		novoUsuario.setTipo(TipoUsuarioEnum.ADMINISTRADOR);
		novoUsuario.setSetor(setor);
		
		Usuario salvo = new Usuario();
		salvo.setId(1L);
		salvo.setNome("Teste");
		salvo.setEmail("teste@alpha.com.br");
		salvo.setSenha("senha");
		salvo.setTipo(TipoUsuarioEnum.ADMINISTRADOR);
		salvo.setSetor(setor);
		
		given(service.save(novoUsuario)).willReturn(salvo);
		
	    mockMvc.perform(
    		RestDocumentationRequestBuilders
	    		.post("/usuario/").contentType("application/json")
			    .content(objectMapper.writeValueAsString(novoUsuario)))
	    		.andExpect(status().isOk())
	    		.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.nome", is("Teste")))
				.andExpect(jsonPath("$.senha").doesNotExist())
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("usuario/create", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint())));
	    
		
	}

	@Test
	public void get() throws Exception {
		
		mockMvc.perform(
			RestDocumentationRequestBuilders
			.get("/usuario/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.nome", is("Teste")))
			.andExpect(jsonPath("$.senha").doesNotExist())
			.andExpect(content().contentType("application/json"))
			.andDo(document("usuario/get",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					 pathParameters(parameterWithName("id").description("O id do usuário a ser encontrado")),
					 	responseFields(getDescricaoDosAtributosDoUsuario())
					 	)
					);
	}
	
	@Test
	public void delete() throws Exception {
		
		mockMvc.perform(
			RestDocumentationRequestBuilders
			.delete("/usuario/{id}", 1))
			.andExpect(status().isOk())
			.andDo(document("usuario/delete",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					 pathParameters(
				        parameterWithName("id").description("O id do usuário a ser deletado")
					      )));
		
	}

}
