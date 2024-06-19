package com.cardanoj.api.Transactioncontroller;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardanoj.api.util.CardanoJRandomNameGenerator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api")
@Tag(name = "Transaction Controller")
public class CardanoJSubmitTransactionController {
	@Autowired
	CardanoJRandomNameGenerator randomName;

	@GetMapping("/submit")
	@Operation(
        summary = "Submit Transaction",
        description = "Submits a Cardano transaction to the blockchain.",
        operationId = "submitTransaction"
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
		return CardanoJSubmitTransactionController.class.getClassLoader().getResource("").getPath();
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