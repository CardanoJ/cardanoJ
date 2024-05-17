package com.cardanoJ.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CardanoJCalculateFee {
    public String calculateMinimumFee(String cliPath, String network, String networkId, String txBuild, String protocolParam, String txInCount, String txOutCount, String byronWitnessCount){

        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "calculate-min-fee",
                    "--tx-body-file", txBuild,
                    network, networkId,
                    "--protocol-params-file", protocolParam,
                    "--tx-in-count", txInCount, //"1",
                    "--tx-out-count", txOutCount, //"2",
                    "--witness-count",byronWitnessCount // "0"
            );

            System.out.println("command: "+processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return reader.readLine();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
