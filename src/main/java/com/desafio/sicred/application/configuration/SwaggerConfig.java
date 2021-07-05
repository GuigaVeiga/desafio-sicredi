package com.desafio.sicred.application.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import java.sql.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";
    public static final String NOME_DESAFIO_SICRED_SERVICE = "desafio-sicred-service";
    public static final String DESCRICAO_SERVICO = "API criada para realizar operações relacionadas a votação de pautas em assembleias";
    public static final String VERSAO_SERVICO = "1.0.0";

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(NOME_DESAFIO_SICRED_SERVICE)
                .description(DESCRICAO_SERVICO)
                .version(VERSAO_SERVICO)
                .contact(new Contact("Guilherme Siqueira", "guilhermesiqueira.com.br", "siqguilherme@gmail.com"))
                .build();

    }

    @Bean
    public Docket customImplementation() {


        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .apiInfo(apiInfo())
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Date.class)
                .directModelSubstitute(java.time.LocalDate.class, Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .useDefaultResponseMessages(false);

        docket = docket.select()
                .paths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();

        return docket;

    }

}
