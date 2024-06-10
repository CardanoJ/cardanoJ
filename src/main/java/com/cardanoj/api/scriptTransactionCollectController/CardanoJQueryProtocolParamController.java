package com.cardanoj.api.scriptTransactionCollectController;
import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CardanoJQueryProtocolParamController {
    @GetMapping("/protocolparam")
    public String getProtocol(){
        return queryProtocolParam();
    }

     public String queryProtocolParam(){
        String resourcePath = getResourcePath();
        String protocolParam = resourcePath + "protocol-parameters.json";
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "protocol-parameters",
                    TESTNET, "2",
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
                      return new String(Files.readAllBytes(txFile.toPath()));
            } else {
                System.err.println("Error: Failed to generate protocol parameters.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    return protocolParam;
    }
    private static String getResourcePath() {
        try {
            URI resourceUri = CardanoJQueryProtocolParamController.class.getClassLoader().getResource("").toURI();
            Path resourcePath = Paths.get(resourceUri);
            return resourcePath.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get resource path", e);
        }
    }
}
