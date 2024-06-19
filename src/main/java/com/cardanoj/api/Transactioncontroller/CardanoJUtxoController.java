package com.cardanoj.api.Transactioncontroller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardanoj.api.util.TransactionDetails;
import com.fasterxml.jackson.databind.ObjectMapper; // Import ObjectMapper from Jackson

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

@RestController
@RequestMapping("/api")
// @Tag(name = "Cardano UTXO", description = "Operations related to Cardano UTXO")
@Tag(name = "Transaction Controller")
public class CardanoJUtxoController {

    @GetMapping("/queryutxo/{address}")
	@Operation(
        summary = "Get UTXO by address",
        description = "Returns a list of UTXO transaction details for the given address"
    )

	@ApiResponse(
		responseCode = "200",
        description = "Success | OK"
	)
	// @ApiResponse(
	// 	responseCode = "404",
    //     description = "Not Found"
	// )
	@ApiResponse(
		responseCode = "500",
        description = "Internal Error"
	)

    public List<TransactionDetails> getOutput(@PathVariable String address) {
        String output = executeCommand(address); // Get transaction details from the command line
        
        // Parse the output into a list of TransactionDetails
        List<TransactionDetails> transactionDetailsList = parseOutput(output);

        return transactionDetailsList;
    }

	private List<TransactionDetails> parseOutput(String output) {
		List<TransactionDetails> transactionDetailsList = new ArrayList<>();

		String[] lines = output.split("\n");
		for (String line : lines) {
			// Skip empty or header lines
			if (line.trim().isEmpty() || line.contains("TxHash") || line.contains("TxIx")) {
				continue;
			}
			// Use regular expression to split the line by whitespace
			String[] parts = line.trim().split("\\s+");
			if (parts.length >= 4) { // Assuming at least four parts in each line
				try {
					// Concatenate additionalInfo if there are more than 4 parts
					StringBuilder additionalInfo = new StringBuilder();
					for (int i = 5; i < parts.length; i++) {
						additionalInfo.append(parts[i]).append(" ");
					}
					// Attempt to parse the transaction index as an integer
					int txID = Integer.parseInt(parts[1]);

					double amount = Double.parseDouble(parts[2]);

					// If successful, create TransactionDetails object and add to the list

					TransactionDetails details = new TransactionDetails(parts[0], txID, String.format("%.0f", amount), additionalInfo.toString().trim());

					// TransactionDetails details = new TransactionDetails(parts[0], txID, Double.parseDouble(parts[2]), additionalInfo.toString().trim());


					transactionDetailsList.add(details);
				} catch (NumberFormatException e) {
					// If parsing fails, log an error and skip this line
					System.err.println("Skipping line due to invalid transaction index: " + line);
				}
			} else {
				// If the line does not contain at least four parts, log an error and skip this line
				System.err.println("Skipping line due to invalid format: " + line);
			}
		}
		
		return transactionDetailsList;
	}
	

    public String executeCommand(String address) {
        StringBuilder outputBuilder = new StringBuilder();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "utxo",
                    "--socket-path", socketPath,
                    "--address", address,
                    TESTNET, "2");

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }

            process.waitFor();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return outputBuilder.toString();
    }
}