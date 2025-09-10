package com.aiquant.cardanoJ.example.dapp2;

import com.cardanoJ.address.CardanoJBuildAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Value("${cardano.cli.path}")
    private String cliPath;

    @GetMapping("/ping")
    public ResponseEntity<String> response() {
        return ResponseEntity.ok("pong");
    }

    @PostMapping("/address/generate")
    public ResponseEntity<Map<String, String>> generateAddress(@RequestBody AddressRequest addressRequest) {
        logger.info("cliPath: " + cliPath);
        CardanoJBuildAddress cardanoJBuildAddress = new CardanoJBuildAddress();
        Map<String, String> response = new HashMap<>();
        try {
            String address = cardanoJBuildAddress.addressGen(
                    cliPath,
                    addressRequest.getResourcePath(),
                    addressRequest.getName(),
                    addressRequest.getNetwork(),
                    addressRequest.getNetworkId()
            );

            String addressActual = new String(
                    Files.readAllBytes(Paths.get(address)),
                    StandardCharsets.UTF_8
            ).trim();

            response.put("content", addressActual);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error generating address", e);
            response.put("content", "Failed to generate address: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
