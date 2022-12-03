package demo.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket notSecuredApi() {

        ApiInfo apiInfo = new ApiInfo(
                "Rest API demonstration",
                """
                        <div>
                            <strong>Auth token mappings:</strong><ul><li>1234 - performance_viewer role</li><li>9999 - performance_admin role</li></ul>
                        </div>
                        <div>
                            <string>Basic authentication:</strong><ul><li>user / password</li></ul>
                        </div>
                        """,

                "0.1",
                "http://onet.pl",
                new Contact("Andrzej Nowak", "http://www.nowak.com", "nowak@com.pl"),
                "Apache 2.0",
                "http://www.apache.com",
                List.of()
        );
        //ApiInfo.DEFAULT;


        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("default")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
//                .paths((PathSelectors.ant("/user/*/performances/**")).negate())
                .build()
                .apiInfo(apiInfo)
                .useDefaultResponseMessages(false)
                .securitySchemes(List.of(authTokenScheme(), basicAuthScheme()))
                .securityContexts(List.of(authTokenSecurityContext(), basicAuthSecurityContext()));
    }


    @Component
    @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
    public class OperationNotesResourcesReader implements springfox.documentation.spi.service.OperationBuilderPlugin {

        private final DescriptionResolver descriptions;

        @Autowired
        public OperationNotesResourcesReader(DescriptionResolver descriptions) {
            this.descriptions = descriptions;
        }

        @Override
        public void apply(OperationContext context) {
            try {
                Optional<RolesAllowed> preAuthorizeAnnotation = context.findAnnotation(RolesAllowed.class);
                if (preAuthorizeAnnotation.isPresent()) {
                    var apiRoleAccessNoteText = "<strong>Requires Role(s):</strong> " + String.join(", ", preAuthorizeAnnotation.get().value());
                    context.operationBuilder().notes(descriptions.resolve(apiRoleAccessNoteText));
                }
            } catch (Exception e) {
                log.error("Error when creating swagger documentation for security roles: " + e);
            }
        }

        @Override
        public boolean supports(DocumentationType delimiter) {
            return SwaggerPluginSupport.pluginDoesApply(delimiter);
        }
    }

    private ApiKey authTokenScheme() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

    private BasicAuth basicAuthScheme() {
        return new BasicAuth("basicAuth");
    }

    private SecurityContext authTokenSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(List.of(new SecurityReference("apiKey", new AuthorizationScope[0])))
                .forPaths(PathSelectors.ant("/users/*/performances/**"))
                .build();
    }

    private SecurityContext basicAuthSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(List.of(new SecurityReference("basicAuth", new AuthorizationScope[0])))
                .forPaths(PathSelectors.ant("/cars/*/service-details"))
                .build();
    }

}