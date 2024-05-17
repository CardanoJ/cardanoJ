package com.cardanoJ.stake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CardanoJQueryStakePool {

    public String queryStakePool(String cliPath, String socketPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "stake-pools",
                    "--testnet-magic", "2",
                    "--socket-path", socketPath
            );
            System.out.println("QueryStakePool: " + processBuilder.command());

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));


            process.waitFor();
            return reader.readLine();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}