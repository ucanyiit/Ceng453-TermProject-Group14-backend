package ceng453.backend.startup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class of Swagger
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfigurer {

    /**
     * This method defines the Docket bean, and controls the endpoints.
     * @return instance of Docket
     */
    @Bean
    public Docket swaggerConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.regex("/.*"))
                .apis(RequestHandlerSelectors.basePackage("ceng453.backend"))
                //.apis(RequestHandlerSelectors.any())
                //.paths(PathSelectors.)
                .build()
                .apiInfo(endPointDetails());
    }

    /**
     * This method is used for changing default values. It provides custom information
     * @return ApiInfo instance
     */

    private ApiInfo endPointDetails() {
        return new ApiInfoBuilder().title("Ceng453 Term Project - Game Server")
                .description("Simple Multi-Player Monopoly Game API using Spring Boot and MariaDB")
                .license("Free")
                .version("1.0")
                .build();
    }
}