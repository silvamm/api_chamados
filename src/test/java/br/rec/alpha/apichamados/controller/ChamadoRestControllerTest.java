package br.rec.alpha.apichamados.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.rec.alpha.apichamados.enumm.PrioridadeChamadoEnum;
import br.rec.alpha.apichamados.enumm.StatusChamadoEnum;
import br.rec.alpha.apichamados.model.Chamado;
import br.rec.alpha.apichamados.model.ChamadoPredefinido;
import br.rec.alpha.apichamados.model.Usuario;
import br.rec.alpha.apichamados.service.ChamadoService;

@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
public class ChamadoRestControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	@MockBean
	private ChamadoService service;
	
	private Chamado chamado;
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(restDocumentation).uris()
		                .withScheme("https")
		                .withHost("softrec.com.br")
		                .withPort(443))
				.build();
		
		chamado = new Chamado();
		chamado.setId(1L);
		chamado.setCriadoEm(LocalDateTime.now());
		chamado.setEncerradoEm(LocalDateTime.now());
		chamado.setDescricao("Descrição");
		chamado.setProtocolo("Protocolo");
		chamado.setStatus(StatusChamadoEnum.PENDENTE);
		chamado.setPrioridade(PrioridadeChamadoEnum.NORMAL);
		
		ChamadoPredefinido definicao = new ChamadoPredefinido();
		definicao.setId(1L);
		chamado.setPredefinicao(definicao);
		
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		chamado.setCriadoPor(usuario);
		
		given(service.findById(chamado.getId())).willReturn(Optional.of(chamado));
	}
	
	private FieldDescriptor[] getDescricaoDosAtributosDoChamado() {
	    return new FieldDescriptor[]{
	    			fieldWithPath("id").description("O identificador único do chamado").type(JsonFieldType.NUMBER),
	                fieldWithPath("criadoEm").description("Data de criacao do chamado").type(JsonFieldType.STRING),
	                fieldWithPath("encerradoEm").description("Data de encerramento do chamado").type(JsonFieldType.STRING),
	                fieldWithPath("descricao").description("A descrição do chamado").type(JsonFieldType.STRING),
	                fieldWithPath("protocolo").description("O protocolo do chamado").type(JsonFieldType.STRING),
	                fieldWithPath("status").description("Status do chamado. (PENDENTE, VISUALIZADO, CANCELADO, ENCERRADO) ").type(JsonFieldType.STRING),
	                fieldWithPath("prioridade").description("Nivel de prioridade do chamado. (NORMAL, URGENTE, CRITICO) ").type(JsonFieldType.STRING),
	                subsectionWithPath("predefinicao").description("Definição do chamado").type(JsonFieldType.OBJECT),
	                subsectionWithPath("criadoPor").description("Usuário que criou o chamado").type(JsonFieldType.OBJECT)
	    };
	}
	
	private FieldDescriptor[] getDescricaoDosAtributosDeUmaListaDeChamados() {
	    return new FieldDescriptor[]{
	    			fieldWithPath("[]").description("Lista de Definições").type(JsonFieldType.ARRAY),
	    			subsectionWithPath("[].id").description("O identificador único do chamado").type(JsonFieldType.NUMBER),
	    			subsectionWithPath("[].criadoEm").description("Data de criacao do chamado").type(JsonFieldType.STRING).optional(),
	    			subsectionWithPath("[].encerradoEm").description("Data de encerramento do chamado").type(JsonFieldType.STRING).optional(),
	    			subsectionWithPath("[].descricao").description("A descrição do chamado").type(JsonFieldType.STRING),
	    			subsectionWithPath("[].protocolo").description("O protocolo do chamado").type(JsonFieldType.STRING).optional(),
	    			subsectionWithPath("[].status").description("Status do chamado. (PENDENTE, VISUALIZADO, CANCELADO, ENCERRADO) ").type(JsonFieldType.STRING).optional(),
	    			subsectionWithPath("[].prioridade").description("Nivel de prioridade do chamado. (NORMAL, URGENTE, CRITICO) ").type(JsonFieldType.STRING).optional(),
	    			subsectionWithPath("[].predefinicao").description("Definição do chamado").type(JsonFieldType.OBJECT),
	    			subsectionWithPath("[].predefinicao.id").description("O identificar únido da definição do chamado").type(JsonFieldType.NUMBER),
	    			subsectionWithPath("[].criadoPor").description("Usuário que criou o chamado").type(JsonFieldType.OBJECT),
	    			subsectionWithPath("[].criadoPor.id").description("Usuário que criou o chamado").type(JsonFieldType.NUMBER)
	    };
	}
	
	@Test
	public void list() throws Exception {

		Chamado chamado2 = new Chamado();
		chamado2.setId(2L);
		chamado2.setCriadoEm(LocalDateTime.now());
		chamado2.setEncerradoEm(LocalDateTime.now());
		chamado2.setDescricao("Descrição");
		chamado2.setProtocolo("Protocolo");
		chamado2.setStatus(StatusChamadoEnum.VISUALIZADO);
		chamado2.setPrioridade(PrioridadeChamadoEnum.URGENTE);
		
		ChamadoPredefinido definicao = new ChamadoPredefinido();
		definicao.setId(1L);
		chamado2.setPredefinicao(definicao);
		
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		chamado2.setCriadoPor(usuario);
		
		List<Chamado> chamados = new ArrayList<>();
		chamados.add(chamado);
		chamados.add(chamado2);
		
		given(service.listAll()).willReturn(chamados);
		
	   mockMvc.perform(
    		RestDocumentationRequestBuilders
                .get("/chamado/"))
        		.andExpect(status().isOk())
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("chamado/list", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint()),
	    				responseFields(getDescricaoDosAtributosDeUmaListaDeChamados())));
		
	}
	
    @Test
    public void update() throws Exception {
    	
    	chamado.setDescricao("Descrição Atualizada");
    	
		given(service.save(chamado)).willReturn(chamado);

        mockMvc.perform(
    		RestDocumentationRequestBuilders
                .put("/chamado/{id}", 1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(chamado)))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.descricao", is("Descrição Atualizada")))
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("chamado/update", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint())));
    }
	
	@Test
	public void create() throws JsonProcessingException, Exception {
		
		Chamado novoChamado = new Chamado();
		chamado.setDescricao("Descrição");
		
		ChamadoPredefinido definicao = new ChamadoPredefinido();
		definicao.setId(1L);
		chamado.setPredefinicao(definicao);
		
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		chamado.setCriadoPor(usuario);

		Chamado salvo = new Chamado();
		salvo.setId(1L);
		salvo.setCriadoEm(LocalDateTime.now());
		salvo.setEncerradoEm(LocalDateTime.now());
		salvo.setDescricao("Descrição");
		salvo.setProtocolo("Protocolo");
		salvo.setStatus(StatusChamadoEnum.PENDENTE);
		salvo.setPrioridade(PrioridadeChamadoEnum.NORMAL);
		chamado.setPredefinicao(definicao);
		chamado.setCriadoPor(usuario);

		
		given(service.save(novoChamado)).willReturn(salvo);

	    mockMvc.perform(
    		RestDocumentationRequestBuilders
	    		.post("/chamado/").contentType("application/json")
			    .content(this.objectMapper.writeValueAsString(novoChamado)))
	    		.andExpect(status().isOk())
	    		.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.descricao", is("Descrição")))
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("chamado/create", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint())));
		
	}

	@Test
	public void get() throws Exception {
		
		mockMvc.perform(
			RestDocumentationRequestBuilders
			.get("/chamado/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.descricao", is("Descrição")))
			.andExpect(content().contentType("application/json"))
			.andDo(document("chamado/get",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					 pathParameters(parameterWithName("id").description("O id do chamado a ser encontrado")),
					 	responseFields(getDescricaoDosAtributosDoChamado())
					 	)
					);
	}
	
	@Test
	public void delete() throws Exception {
		
		mockMvc.perform(
			RestDocumentationRequestBuilders
			.delete("/chamado/{id}", 1))
			.andExpect(status().isOk())
			.andDo(document("chamado/delete",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					 pathParameters(
				        parameterWithName("id").description("O id do chamado a ser deletado")
					      )));
		
	}
}
