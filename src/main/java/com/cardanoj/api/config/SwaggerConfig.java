package com.cardanoj.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
// import io.swagger.v3.oas.models.security.SecurityScheme;
// import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){

        // String schemeName = "bearerScheme";
        return new OpenAPI()
                //  .components(new Components()
                //  .addSecuritySchemes(schemeName, new SecurityScheme()
                //             .name(schemeName)
                //             .type(SecurityScheme.Type.HTTP)
                //             .bearerFormat("JWT")
                //             .scheme("bearer")))
   

                 .info(new Info().title("CardanoJ API")
                 .description("This API enables comprehensive interactions with the Cardano blockchain, including UTXO retrieval, transaction construction, signing, and submission. Users can build transactions by specifying sender and receiver addresses, amount, transaction hash, and ID, securely sign transactions using provided keys, and submit the signed transactions to the blockchain for validation and recording.")
                 .summary("Enables comprehensive interactions with the Cardano blockchain, including UTXO retrieval, transaction construction, signing, and submission.")
                 .version("1.0")
                //  .contact(new Contact().name("CardanoJ").email("example@gmail.com").url("https://github.com/CardanoJ/cardanoJ/tree/cardanoj-api"))
                //  .license(new License().name("Apach"))) 
                //  .externalDocs(new ExternalDocumentation().description("CardanoJ Documention").url("null")
                 )      
        ;

    }
}
