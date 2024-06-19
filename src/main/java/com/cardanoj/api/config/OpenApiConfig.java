// package com.cardanoj.api.config;

// import io.swagger.v3.oas.annotations.OpenAPIDefinition;
// import io.swagger.v3.oas.annotations.info.Contact;
// import io.swagger.v3.oas.annotations.info.Info;
// import io.swagger.v3.oas.annotations.info.License;
// // import io.swagger.v3.oas.annotations.security.SecurityScheme;
// import io.swagger.v3.oas.annotations.servers.Server;

// @OpenAPIDefinition(
//     info = @Info(
//         title = "CardanoJ Api",
//         description = "\n" + //
//                         "This API enables comprehensive interactions with the Cardano blockchain, including UTXO retrieval, transaction construction, signing, and submission. Users can build transactions by specifying sender and receiver addresses, amount, transaction hash, and ID, securely sign transactions using provided keys, and submit the signed transactions to the blockchain for validation and recording.",
//         summary = "\n" + //
//                         "Enables comprehensive interactions with the Cardano blockchain, including UTXO retrieval, transaction construction, signing, and submission.",
//         termsOfService = "T&C",
//         contact = @Contact(
//             name = "Quotus",
//             email = "example@gmail.com"
//         ),
//         license = @License(
//             name = "Your Licence"
//         ),
//         version = "V1"                                
    
//     ),
//     servers = {
//             @Server(
//                 description = "devEnv",
//                 url = "http://localhost:8080"
//             ),
//             @Server(
//                 description = "testEnv",
//                 url = "http://localhost:8080"
//             )
//     }

//     //    security =   @SecurityRequirement(
//     //     name = "auth"
//     //    )
// )
// // @SecurityScheme(
// //     name = "auth",
// //     in = SecuritySchemeIn.HEADER,
// //     type = SecuritySchemeType.HTTP,
// //     bearerFormat = "JWT",
// //     description = "security desc"

// // )
// public class OpenApiConfig {

// }
