package com.cardanoj.api.controller;

import java.io.BufferedReader;
import java.util.Random;


import static com.cardanoj.api.util.CJConstant.cliPath;
import static com.cardanoj.api.util.CJConstant.socketPath;
import static com.cardanoj.api.util.CJConstant.TESTNET;

import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")

public class BuildTransactionController {
	@GetMapping("/build/{senderAddress}/{receiverAddress}/{lovelace}/{txHash}/{txID}")
	public String getTransaction(@PathVariable String senderAddress,@PathVariable String receiverAddress,@PathVariable String lovelace,@PathVariable String txHash,@PathVariable String txID) {
	
		return executeTransaction(senderAddress,receiverAddress,lovelace,txHash,txID) ;
		
	}
	public String executeTransaction(String senderAddress,String receiverAddress,String lovelace,String txHash,String txID) {
		
		Random random = new Random();
		int randomNumber = random.nextInt(Integer.MAX_VALUE);

		//int randomNumber = random.nextInt	                  (2147483647 - -2147483648 + 1) + -2147483648;
			String txHashID = txHash + "#" + txID;

	        String tot = receiverAddress + "+" + lovelace+" lovelace";
	         String resourcePath = getResourcePath();
	        String bodyPath = resourcePath + "body_"+randomNumber+".txbody";
 
	        try {
	            ProcessBuilder processBuilder = new ProcessBuilder(
	                    cliPath, "transaction", "build",
	                    "--socket-path", socketPath,
	                    TESTNET.toString(), "2",
	                   "--tx-in",txHashID,
	                    "--tx-out", tot,
	                    "--change-address", senderAddress,
	                    "--out-file",bodyPath
	                  
	            );
                System.out.println("command: "+processBuilder.command());
                System.out.println("path: "+bodyPath.toString());

	            processBuilder.redirectErrorStream(true);
	            Process process = processBuilder.start();
	            process.waitFor();

	            // Read the output of the process
	            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            StringBuilder output = new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                output.append(line).append("\n");
	            }
	            String processOutput = output.toString();

	            // Print the process output
	            System.out.print("Process output:");
	            System.out.println(processOutput);
	            
	            String fileContent = new String(Files.readAllBytes(Paths.get(bodyPath)));

				return fileContent;

		
	        }catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	            return null;
	        }

}
	
    private static String getResourcePath() {
        return BuildTransactionController.class.getClassLoader().getResource("").getPath();
    }

}