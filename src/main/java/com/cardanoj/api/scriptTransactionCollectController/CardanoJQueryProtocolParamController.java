package com.cardanoj.api.scriptTransactionCollectController;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for querying protocol parameters in the Cardano J API application.
 * <p>
 * This controller handles requests to retrieve Cardano protocol parameters by executing a CLI command and returning the output.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CardanoJQueryProtocolParamController {

    /**
     * Handles HTTP GET requests to the endpoint "/api/protocolparam".
     * <p>
     * This method triggers the process to query protocol parameters and returns the result.
     * </p>
     *
     * @return a {@link String} containing the protocol parameters in JSON format
     */
    @GetMapping("/protocolparam")
    public String getProtocol() {
        return queryProtocolParam();
    }

    /**
     * Executes a CLI command to query Cardano protocol parameters and returns the result.
     * <p>
     * This method builds and executes a command to fetch protocol parameters, saves the output to a file, and reads the content of the file.
     * </p>
     *
     * @return a {@link String} containing the protocol parameters in JSON format, or an error message if the process fails
     */
    public String queryProtocolParam() {
        String resourcePath = getResourcePath();
        String protocolParam = resourcePath + "protocol-parameters.json";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "protocol-parameters",
                    TESTNET, "2",
                    "--socket-path", socketPath,
                    "--out-file", protocolParam
            );

            System.out.println("command: " + processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            File txFile = new File(protocolParam);
            if (txFile.exists()) {
                System.out.println("Protocol parameters generated");
                return new String(Files.readAllBytes(txFile.toPath()));
            } else {
                System.err.println("Error: Failed to generate protocol parameters.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to query protocol parameters", e);
        }
        return protocolParam;
    }

    /**
     * Retrieves the path to the resources directory.
     * <p>
     * This method resolves the path to the resources directory using the class loader.
     * </p>
     *
     * @return a {@link String} representing the path to the resources directory
     * @throws RuntimeException if there is an error resolving the resource path
     */
    private static String getResourcePath() {
        try {
            URI resourceUri = CardanoJQueryProtocolParamController.class.getClassLoader().getResource("").toURI();
            Path resourcePath = Paths.get(resourceUri);
            return resourcePath.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get resource path", e);
        }
    }
}
