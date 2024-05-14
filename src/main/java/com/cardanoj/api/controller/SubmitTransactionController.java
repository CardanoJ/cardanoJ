package com.cardanoj.api.controller;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.cardanoj.api.util.CJConstant.cliPath;
import static com.cardanoj.api.util.CJConstant.socketPath;
import static com.cardanoj.api.util.CJConstant.TESTNET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardanoj.api.util.RandomNameGenerator;

@RestController
@RequestMapping("/api")
public class SubmitTransactionController {
	@Autowired
	RandomNameGenerator randomName;

	@GetMapping("/submit")
	public String submitTransaction(@RequestParam String tx) throws UnsupportedEncodingException {
		String decodedTxBody = URLDecoder.decode(tx, "UTF-8");
		String txhash = submit(decodedTxBody);
		// ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = "{\"txhash\":\"" + txhash + "\"}";
		return jsonResponse;

	}

	public String submit(String tx) {
		String lineTXID = "";
		String resourcePath = getResourcePath();

		String txPath = resourcePath + randomName.generate() + ".tx";
		saveToFile(tx, txPath);

		try {
			ProcessBuilder processBuilder = new ProcessBuilder(
					cliPath, "transaction", "submit", TESTNET, "2", "--tx-file", txPath, "--socket-path", socketPath);
			System.out.println("command: " + processBuilder.command());
			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();
			;
			int exitcode = process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			if (exitcode == 0) {
				System.out.println("Executed Successfully");

				// Geting Transaction ID

				ProcessBuilder processBuilderTXID = new ProcessBuilder(
						cliPath, "transaction", "txid", "--tx-file", txPath);
				System.out.println("Commands: " + processBuilderTXID.command());
				processBuilderTXID.redirectErrorStream(true);
				Process processTXID = processBuilderTXID.start();
				int exitcodeTXID = processTXID.waitFor();
				BufferedReader readerTXID = new BufferedReader(new InputStreamReader(processTXID.getInputStream()));

				while ((lineTXID = readerTXID.readLine()) != null) {
					System.out.println("Transaction ID: " + lineTXID);
					System.out.println("Cardanoscan: https://preview.cardanoscan.io/transaction/" + lineTXID);
					break;
				}
				System.out.println("Here: " + lineTXID);
				if (exitcodeTXID == 0) {
					System.out.println("Transaction ID Generated SuccessFully");
					return lineTXID;
				}
			} else {
				System.err.println("Error while generating transaction ID");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return lineTXID;

	}

	private static String getResourcePath() {
		return SubmitTransactionController.class.getClassLoader().getResource("").getPath();
	}

	// Method to save the decoded JSON data to a file
	private void saveToFile(String jsonData, String fileName) {

		try (FileWriter fileWriter = new FileWriter(fileName)) {
			fileWriter.write(jsonData);
			System.out.println("Saved decoded " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}