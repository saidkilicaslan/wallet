package com.inghub.wallet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@Slf4j
public class SwaggerDocumentationConfig {

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }

    public OpenAPI customerAPI(){
        Contact contact = new Contact();
        contact.setName("PiA(people in action)");
        contact.setEmail("info@pia-team.com");
        contact.setUrl("http://www.pia-team.com/");
        return new OpenAPI()
                .info(new Info().title("Orbitant Customer Management API")
                    .description("This is Swagger UI environment generated for the TMF Customer Management specification")
                    .license(new License().name("Apache License Version 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                    .version("4.0.0")
                    .contact(contact));
    }

}
