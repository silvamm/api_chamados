package br.rec.alpha.apichamados.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/").allowedOrigins("*");
				registry.addMapping("/login/").allowedOrigins("*");
				registry.addMapping("/setor/**").allowedOrigins("*");
				registry.addMapping("/usuario/**").allowedOrigins("*");
				registry.addMapping("/chamado/**").allowedOrigins("*");
				registry.addMapping("/chamadopredefinido/**").allowedOrigins("*");
			}
		};
	}

}
