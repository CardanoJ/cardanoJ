package com.cardanoj.api.controller;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import static com.cardanoj.api.util.CJConstant.cliPath;
import static com.cardanoj.api.util.CJConstant.socketPath;
import static com.cardanoj.api.util.CJConstant.TESTNET;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardanoj.api.util.CJConstant;


	@RestController
	@RequestMapping("/api")
	public class CardanoController {
		
		@GetMapping("/queryutxo/{address}")
		public String getOutput(@PathVariable String address) {
		    
		    return executeCommand(address);
		}

	 
	    	private String executeCommand(String address) {
	    		//String address = "addr_test1vr06yexumsxcf26uhdpyawpu9vffcxw0d9xr37vem3sfvwqwpggea";
	            StringBuilder outputBuilder = new StringBuilder();

	    		
	    		try{
	                ProcessBuilder processBuilder = new ProcessBuilder(
	                		
	                        cliPath, "query", "utxo",
	                        "--socket-path",socketPath,
	                        "--address",address,
	                        TESTNET, "2"
	                );

	                System.out.println("command: "+processBuilder.command());
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
	    	
	    	private String parseOutput(String output) {
	    	    StringBuilder parsedOutput = new StringBuilder();
	    	    // Split the output by lines
	    	    String[] lines = output.split("\\r?\\n");
	    	    // Assuming each line contains relevant information in fixed positions
	    	    for (String line : lines) {
	    	        // Example parsing logic, modify according to your actual output format
	    	        String[] parts = line.split("\\s+");
	    	        if (parts.length >= 3) {
	    	            String txHash = parts[0];
	    	            String txIx = parts[1];
	    	            String amount = parts[2];
	    	            parsedOutput.append("TxHash: ").append(txHash).append(", TxIx: ").append(txIx).append(", Amount: ").append(amount).append("\n");
	    	        }
	    	    }
	    	    return parsedOutput.toString();
	    	
	    	
	    	

	    	}
	}
	

    

