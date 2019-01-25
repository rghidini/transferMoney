package com.ghidini.tm.config;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class SwaggerConfig {

	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ghidini.tm.controller"))
				.paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("GRAPH")
				.description("Mapeamento dos Endpoints do projeto")
				.version("V-1.0.0")
				.termsOfServiceUrl("http://terms-of-services.url")
				.build();
	}}
