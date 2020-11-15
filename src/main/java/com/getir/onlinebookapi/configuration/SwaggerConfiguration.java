package com.getir.onlinebookapi.configuration;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${spring.application.name:Api Documentation}")
    private String appName;

    @Value("${swagger.ignoredParameterTypes:#{T(org.apache.commons.lang3.ArrayUtils).EMPTY_CLASS_ARRAY}}")
    private Class[] ignoredParameterTypes;

    private static final ApiKey API_KEY = new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, "header");
    private static final SecurityContext SECURITY_CONTEXT = SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build();

    private static List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes));
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework")))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(API_KEY))
                .securityContexts(Collections.singletonList(SECURITY_CONTEXT))
                .ignoredParameterTypes(ignoredParameterTypes)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                appName,
                "Online Book Api",
                "1.0.0",
                "",
                new Contact("Cengiz", "https://medium.com/@cengizbursali", "cengizbursali@gmail.com"),
                ".",
                ".",
                Collections.singletonList(new StringVendorExtension("vendor", "Getir"))
        );
    }
}
