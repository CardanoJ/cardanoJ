package com.cardanoj.api.stakeController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for querying stake pool information in the Cardano J API application.
 * <p>
 * This controller handles requests to retrieve information about Cardano stake pools by executing a CLI command
 * and returning the output.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CardanoJQueryStakePoolController {

    /**
     * Handles HTTP GET requests to the endpoint "/api/stakepool".
     * <p>
     * This method triggers the process to query stake pools and returns the result in a JSON format.
     * </p>
     *
     * @return a {@link String} containing the stake pool information in JSON format
     */
    @GetMapping("/stakepool")
    public String getStake() {
        String stakepool = queryStakePool();
        String jsonResponse = "{\"stakepool\":\"" + stakepool + "\"}";
        return jsonResponse;
    }

    /**
     * Executes a CLI command to query Cardano stake pools and returns the result.
     * <p>
     * This method builds and executes a command to fetch stake pools information, reads the output, and returns it.
     * </p>
     *
     * @return a {@link String} containing the stake pool information, or {@code null} if an error occurs
     */
    public String queryStakePool() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "stake-pools",
                    TESTNET, "2",
                    "--socket-path", socketPath
            );
            System.out.println("QueryStakePool: " + processBuilder.command());

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print each line
                result.append(line).append("\n"); // Store each line in the StringBuilder
            }

            process.waitFor();
            System.out.println(result.toString());
            return result.toString();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
