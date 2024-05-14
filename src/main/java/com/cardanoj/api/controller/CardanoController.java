package com.cardanoj.api.controller;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cardanoj.api.util.CJConstant.cliPath;
import static com.cardanoj.api.util.CJConstant.socketPath;
import static com.cardanoj.api.util.CJConstant.TESTNET;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cardanoj.api.util.TransactionDetails;

@RestController
@RequestMapping("/api")
public class CardanoController {

	@GetMapping("/queryutxo/{address}")
	public String getOutput(@PathVariable String address) {

		String output = executeCommand(address); // Get transaction details from the command line
		List<TransactionDetails> transactions = parseOutput(output); // Parse the output

		// Convert transaction details to JSON
		StringBuilder jsonBuilder = new StringBuilder("[");
		for (TransactionDetails transaction : transactions) {
			jsonBuilder.append(transaction.toJSON()).append(",");
		}
		jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Remove the last comma
		jsonBuilder.append("]");
		System.out.println(jsonBuilder);

//		return jsonBuilder.toString(); // Return JSON response
		return "Hello";
	}

	private List<TransactionDetails> parseOutput(String output) {
		List<TransactionDetails> transactions = new ArrayList<>();
		Pattern txHashPattern = Pattern.compile("^([a-fA-F0-9]+)\\s+(\\d+)\\s+(.+)$");
		Pattern headerPattern = Pattern.compile("^\\s*TxHash\\s+TxIx\\s+Amount\\s*$");
		// Pattern additionalPattern =
		// Pattern.compile("TxOutDatumHash\\s+([\\w\\s]+)\\s+\"([a-fA-F0-9]+)\"");

		boolean headerPassed = false;
		String[] lines = output.split("\n");
		for (String line : lines) {
			if (!headerPassed) {
				Matcher headerMatcher = headerPattern.matcher(line);
				if (headerMatcher.matches()) {
					headerPassed = true;
				}
				continue;
			}
			Matcher txMatcher = txHashPattern.matcher(line);
			if (txMatcher.matches()) {
				String txHash = txMatcher.group(1);
				int txIx = Integer.parseInt(txMatcher.group(2));
				String amount = txMatcher.group(3);
				// Check if the amount contains "lovelace"
				int plusIndex = amount.indexOf("lovelace +");
				String additionalInfo = null;
				if (plusIndex != -1) {
					// If "lovelace" is found, extract the part after it as additionalInfo
					additionalInfo = amount.substring(plusIndex + "lovelace +".length()).trim();
					amount = amount.substring(0, plusIndex).trim();
				}
				transactions.add(new TransactionDetails(txHash, txIx, amount, additionalInfo));
				System.out.println(additionalInfo + "--------------------------------");

			}
		}
		return transactions;
	}

	public String executeCommand(String address) {
		// String address =
		// "addr_test1vr06yexumsxcf26uhdpyawpu9vffcxw0d9xr37vem3sfvwqwpggea";
		StringBuilder outputBuilder = new StringBuilder();

		try {
			ProcessBuilder processBuilder = new ProcessBuilder(

					cliPath, "query", "utxo",
					"--socket-path", socketPath,
					"--address", address,
					TESTNET, "2");

			System.out.println("command: " + processBuilder.command());
			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				outputBuilder.append(line).append("\n");
				System.out.println(line);
			}

			process.waitFor();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return outputBuilder.toString();

	}

}