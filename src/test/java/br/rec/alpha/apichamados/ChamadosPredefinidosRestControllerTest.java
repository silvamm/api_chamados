package br.rec.alpha.apichamados;

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

import br.rec.alpha.apichamados.model.ChamadoPredefinido;
import br.rec.alpha.apichamados.service.ChamadoPredefinidosService;

@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
public class ChamadosPredefinidosRestControllerTest {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	@MockBean
	private ChamadoPredefinidosService service;
	
	private ChamadoPredefinido definicao;
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(restDocumentation).uris()
		                .withScheme("http")
		                .withHost("softrec.com.br")
		                .withPort(8080))
				.build();
		
		definicao = new ChamadoPredefinido();
		definicao.setId(1L);
		definicao.setNome("Teste");
    	
		given(service.findById(definicao.getId())).willReturn(Optional.of(definicao));
	}
	
	private FieldDescriptor[] getDescricaoDosAtributosDaDefinicao() {
	    return new FieldDescriptor[]{fieldWithPath("id").description("O identificador único da definição").type(JsonFieldType.NUMBER),
	                fieldWithPath("nome").description("O nome da definição").type(JsonFieldType.STRING),
	    };
	}
	
	private FieldDescriptor[] getDescricaoDosAtributosDeUmaListaDasDefinicoes() {
	    return new FieldDescriptor[]{
	    			fieldWithPath("[]").description("Lista de Definições").type(JsonFieldType.ARRAY),
	    			subsectionWithPath("[].id").description("O identificador único da definição").type(JsonFieldType.NUMBER),
	                subsectionWithPath("[].nome").description("O nome da definição").type(JsonFieldType.STRING),
	    };
	}
	
	@Test
	public void list() throws Exception {

		ChamadoPredefinido definicao2 = new ChamadoPredefinido();
		definicao2.setId(2L);
		definicao2.setNome("Teste 2");
		
		List<ChamadoPredefinido> definicoes = new ArrayList<>();
		definicoes.add(definicao);
		definicoes.add(definicao2);
		
		given(service.listAll()).willReturn(definicoes);
		
	   mockMvc.perform(
    		RestDocumentationRequestBuilders
                .get("/chamadopredefinido/"))
        		.andExpect(status().isOk())
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("definicao/list", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint()),
	    				responseFields(getDescricaoDosAtributosDeUmaListaDasDefinicoes())));
		
	}
	
    @Test
    public void update() throws Exception {
    	
    	definicao.setNome("Teste Atualizado");
    	
		given(service.save(definicao)).willReturn(definicao);

        mockMvc.perform(
    		RestDocumentationRequestBuilders
                .put("/chamadopredefinido/{id}", 1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(definicao)))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.nome", is("Teste Atualizado")))
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("definicao/update", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint())));
    }
	
	@Test
	public void create() throws JsonProcessingException, Exception {
		
		ChamadoPredefinido novaDefinicao = new ChamadoPredefinido();
		novaDefinicao.setNome("Teste");

		ChamadoPredefinido salvo = new ChamadoPredefinido();
		salvo.setId(1L);
		salvo.setNome("Teste");
		
		given(service.save(novaDefinicao)).willReturn(salvo);

	    mockMvc.perform(
    		RestDocumentationRequestBuilders
	    		.post("/chamadopredefinido/").contentType("application/json")
			    .content(this.objectMapper.writeValueAsString(novaDefinicao)))
	    		.andExpect(status().isOk())
	    		.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.nome", is("Teste")))
	    		.andExpect(content().contentType("application/json"))
	    		.andDo(document("definicao/create", 
	    				preprocessRequest(prettyPrint()),
	    				preprocessResponse(prettyPrint())));
		
	}

	@Test
	public void get() throws Exception {
		
		mockMvc.perform(
			RestDocumentationRequestBuilders
			.get("/chamadopredefinido/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.nome", is("Teste")))
			.andExpect(content().contentType("application/json"))
			.andDo(document("definicao/get",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					 pathParameters(parameterWithName("id").description("O id da definição a ser encontrada")),
					 	responseFields(getDescricaoDosAtributosDaDefinicao())
					 	)
					);
	}
	
	@Test
	public void delete() throws Exception {
		
		mockMvc.perform(
			RestDocumentationRequestBuilders
			.delete("/chamadopredefinido/{id}", 1))
			.andExpect(status().isOk())
			.andDo(document("definicao/delete",
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					 pathParameters(
				        parameterWithName("id").description("O id da definição a ser deletada")
					      )));
		
	}

}
