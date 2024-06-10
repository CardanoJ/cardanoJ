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

@RestController
@RequestMapping("/api")
public class CardanoJQueryStakePoolController {
    @GetMapping("/stakepool")
    public String getStake(){
        String stakepool = queryStakePool();
     String jsonResponse = "{\"stakepool\":\"" + stakepool + "\"}";
        return jsonResponse ;

    }

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
            return result.toString() ;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
