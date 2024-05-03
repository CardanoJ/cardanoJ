package com.cardanoJ;

import java.io.File;

public class temp {
    static String cliPath = "src/main/resources/bin/cardano-cli";
    static String network = "--testnet-magic";

    private static String getResourcePath() {
        return temp.class.getClassLoader().getResource("").getPath();
    }



    public static void main(String[] args) {
            String resourcePath = getResourcePath();
            String protocolParam= resourcePath + "protocol-parameters.json";
            String socketPath = "/home/tarachand/preview/node.socket";

            try{
                ProcessBuilder processBuilder = new ProcessBuilder(
                        cliPath, "query", "protocol-parameters",
                        network, "2",
                        "--socket-path", socketPath,
                        "--out-file",protocolParam
                );

                System.out.println("command: "+processBuilder.command());
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                process.waitFor();

                File txFile = new File(protocolParam);
                if (txFile.exists()) {
                    System.out.println("protocol parameters generated");
                } else {
                    System.err.println("Error: Failed to generate protocol parameters.");
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }



