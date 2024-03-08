package org.cardanoJ.utils;

import java.io.*;


public class Address {

    public static void generateAddress(String name,String network, String id) {

        try {
            // Path to the cardano-cli executable within your project directory
            String path = "src/main/resources/bin/";
            String cliPath = path + "cardano-cli";



            String[] signingKey = {cliPath, "address", "key-gen", "--verification-key-file", name + ".vkey",
                    "--signing-key-file", name + ".skey"};


            String[] addressKey;
            if (network.equals("--mainnet")) {
                addressKey = new String[]{cliPath, "address", "build",
                        "--payment-verification-key-file", name + ".vkey", network,
                        "--out-file", name + ".addr"};
            } else {
                addressKey = new String[]{cliPath, "address", "build",
                        "--payment-verification-key-file", name + ".vkey", network, id,
                        "--out-file", name + ".addr"};
            }


            ProcessBuilder pb1 = new ProcessBuilder(signingKey);
            ProcessBuilder pb2 = new ProcessBuilder(addressKey);

            Process process1 = pb1.start();
            int exitCode1 = process1.waitFor();
            if (exitCode1 == 0) {
                readOutput(process1.getInputStream(), process1.getErrorStream());
                System.out.println("Exited with error code for signingKey: " + exitCode1);
                Process process2 = pb2.start();
                readOutput(process2.getInputStream(), process2.getErrorStream());
                int exitCode2 = process2.waitFor();
                System.out.println("Exited with error code for addressKey: " + exitCode2);
            } else {
                System.out.println("addressKey not executed successfully");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void readOutput(InputStream inputStream, InputStream errorStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));


        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }


        while ((line = errorReader.readLine()) != null) {
            System.out.println(line);
        }
    }

}

