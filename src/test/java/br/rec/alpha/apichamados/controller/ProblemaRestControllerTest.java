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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

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

import br.rec.alpha.apichamados.model.Problema;
import br.rec.alpha.apichamados.service.ProblemaService;

@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
public class ProblemaRestControllerTest {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	@MockBean
	private ProblemaService service;
	
	private Problema problema;
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(restDocumentation).uris()
		                .withScheme("https")
		                .withHost("softrec.com.br")
		                .withPort(443))
				.build();
		
		problema = new Problema();
		problema.setId(1L);
		problema.setNome("Teste");
    	
		given(service.findById(problema.getId())).willReturn(Optional.of(problema));
	}
	
	private FieldDescriptor[] getDescricaoDosAtributosDoProblema() {
	    return new FieldDescriptor[]{fieldWithPath("id").description("O identificador único do problema").type(JsonFieldType.NUMBER),
	                fieldWithPath("nome").description("O nome do problema").type(JsonFieldType.STRING),
	    };
	}
	
	private FieldDescriptor[] getDescricaoDosAtributosDeUmaListaDosProblemas() {
	    return new FieldDescriptor[]{
	    			fieldWithPath("[]").description("Lista de problemas").type(JsonFieldType.ARRAY),
	    			subsectionWithPath("[].id").description("O identificador único do problema").type(JsonFieldType.NUMBER),
	                subsectionWithPath("[].nome").description("O nome do problema").type(JsonFieldType.STRING),
	    };
	}
	
	@Test
	public void list() throws Exception {

		Problema problema2 = new Problema();
		problema2.setId(2L);
		problema2.setNome("Teste 2");
		
		List<Problema> problemas = new ArrayList<>();
		problemas.add(problema);
		problemas.add(problema2);
		
		given(service.listAll()).willReturn(problemas);
		
	   mockMvc.perform(
    		RestDocumentationRequestBuilders
                .get("/problema/"))
        		.andExpect(status().isOk())
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("problema/list", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint()),
	    				responseFields(getDescricaoDosAtributosDeUmaListaDosProblemas())));
		
	}
	
    @Test
    public void update() throws Exception {
    	
    	problema.setNome("Teste Atualizado");
    	
		given(service.save(problema)).willReturn(problema);

        mockMvc.perform(
    		RestDocumentationRequestBuilders
                .put("/problema/{id}", 1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(problema)))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.nome", is("Teste Atualizado")))
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("problema/update", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint())));
    }
	
	@Test
	public void create() throws JsonProcessingException, Exception {
		
		Problema novoProblema = new Problema();
		novoProblema.setNome("Teste");

		Problema salvo = new Problema();
		salvo.setId(1L);
		salvo.setNome("Teste");
		
		given(service.save(novoProblema)).willReturn(salvo);

	    mockMvc.perform(
    		RestDocumentationRequestBuilders
	    		.post("/problema/").contentType("application/json")
			    .content(this.objectMapper.writeValueAsString(novoProblema)))
	    		.andExpect(status().isOk())
	    		.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.nome", is("Teste")))
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("problema/create", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint())));
		
	}

	@Test
	public void get() throws Exception {
		
		mockMvc.perform(
			RestDocumentationRequestBuilders
			.get("/problema/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.nome", is("Teste")))
			.andExpect(content().contentType("application/json"))
			.andDo(document("problema/get",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					 pathParameters(parameterWithName("id").description("O id do problema a ser encontrada")),
					 	responseFields(getDescricaoDosAtributosDoProblema())
					 	)
					);
	}
	
	@Test
	public void delete() throws Exception {
		
		mockMvc.perform(
			RestDocumentationRequestBuilders
			.delete("/problema/{id}", 1))
			.andExpect(status().isOk())
			.andDo(document("problema/delete",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					 pathParameters(
				        parameterWithName("id").description("O id do problema a ser deletada")
					      )));
		
	}

}
