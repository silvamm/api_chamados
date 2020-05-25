package br.rec.alpha.apichamados.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.RequestDispatcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
public class ExemploErroTest {
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(restDocumentation).uris()
		                .withScheme("https")
		                .withHost("softrec.com.br")
		                .withPort(8080))
				.build();
		
	}
	
	@Test
    public void exemploDeErro() throws Exception {
				
		mockMvc.perform(
				RestDocumentationRequestBuilders
					.get("/error/")
	    			.contentType("application/json")
	    			.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
					.requestAttr(RequestDispatcher.ERROR_REQUEST_URI,
							"/usuario/")
					.requestAttr(RequestDispatcher.ERROR_MESSAGE,
							"A senha esta em branco")
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("error", is("Bad Request")))
				.andExpect(jsonPath("timestamp", is(notNullValue())))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("path", is(notNullValue())))
				.andDo(document("error",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						responseFields(
								fieldWithPath("error").description("O nome do erro HTTP, exemplo: `Bad Request`"),
								fieldWithPath("message").description("A descrição da causa do erro"),
								fieldWithPath("path").description("O endpoint que o erro aconteceu"),
								fieldWithPath("status").description("O código do erro HTTP, exemplo: `400`"),
								fieldWithPath("timestamp").description("O tempo, em milisegundos, que o erro aconteceu")
								)
							)
						);
	  
    }
	
}
