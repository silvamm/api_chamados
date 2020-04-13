package br.rec.alpha.apichamados.controller;

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

import br.rec.alpha.apichamados.model.Setor;
import br.rec.alpha.apichamados.service.SetorService;

@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
public class SetorRestControllerTest {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	@MockBean
	private SetorService service;
	
	private Setor setor;
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(restDocumentation).uris()
		                .withScheme("http")
		                .withHost("softrec.com.br")
		                .withPort(8080))
				.build();
		
    	setor = new Setor();
    	setor.setId(1L);
    	setor.setNome("Teste");
    	
		given(service.findById(setor.getId())).willReturn(Optional.of(setor));
	}
	
	private FieldDescriptor[] getDescricaoDosAtributosDoSetor() {
	    return new FieldDescriptor[]{fieldWithPath("id").description("O identificador único do setor").type(JsonFieldType.NUMBER),
	                fieldWithPath("nome").description("O nome do setor").type(JsonFieldType.STRING),
	    };
	}
	
	private FieldDescriptor[] getDescricaoDosAtributosDeUmaListaDeSetores() {
	    return new FieldDescriptor[]{
	    			fieldWithPath("[]").description("Lista de setores").type(JsonFieldType.ARRAY),
	    			subsectionWithPath("[].id").description("O identificador único do setor").type(JsonFieldType.NUMBER),
	                subsectionWithPath("[].nome").description("O nome do setor").type(JsonFieldType.STRING),
	    };
	}
	
	@Test
	public void list() throws Exception {

		Setor setor2 = new Setor();
		setor2.setId(2L);
		setor2.setNome("Teste 2");
		
		List<Setor> setores = new ArrayList<>();
		setores.add(setor);
		setores.add(setor2);
		
		given(service.listAll()).willReturn(setores);
		
	   mockMvc.perform(
    		RestDocumentationRequestBuilders
                .get("/setor/"))
        		.andExpect(status().isOk())
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("setor/list", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint()),
	    				responseFields(getDescricaoDosAtributosDeUmaListaDeSetores())));
		
	}
	
    @Test
    public void update() throws Exception {
    	
    	setor.setNome("Teste Atualizado");
    	
		given(service.save(setor)).willReturn(setor);

        mockMvc.perform(
    		RestDocumentationRequestBuilders
                .put("/setor/{id}", 1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(setor)))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.nome", is("Teste Atualizado")))
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("setor/update", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint())));
    }
	
	@Test
	public void create() throws JsonProcessingException, Exception {
		
		Setor novoSetor = new Setor();
		novoSetor.setNome("Teste");

		Setor salvo = new Setor();
		salvo.setId(1L);
		salvo.setNome("Teste");
		
		given(service.save(novoSetor)).willReturn(salvo);

	    mockMvc.perform(
    		RestDocumentationRequestBuilders
	    		.post("/setor/").contentType("application/json")
			    .content(this.objectMapper.writeValueAsString(novoSetor)))
	    		.andExpect(status().isOk())
	    		.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.nome", is("Teste")))
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("setor/create", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint())));
		
	}

	@Test
	public void get() throws Exception {
		
		mockMvc.perform(
			RestDocumentationRequestBuilders
			.get("/setor/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.nome", is("Teste")))
			.andExpect(content().contentType("application/json"))
			.andDo(document("setor/get",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					 pathParameters(parameterWithName("id").description("O id do setor a ser encontrado")),
					 	responseFields(getDescricaoDosAtributosDoSetor())
					 	)
					);
	}
	
	@Test
	public void delete() throws Exception {
		
		mockMvc.perform(
			RestDocumentationRequestBuilders
			.delete("/setor/{id}", 1))
			.andExpect(status().isOk())
			.andDo(document("setor/delete",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					 pathParameters(
				        parameterWithName("id").description("O id do setor a ser deletado")
					      )));
		
	}

}
