package com.aiquant.cardanoJ.example.dapp2;

import com.cardanoJ.transaction.CardanoJTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Value("${cardano.cli.path}")
    private String cliPath;

    @Value("${cardano.socket.path}")
    private String socketPath;

    @PostMapping("/transaction/direct")
    public ResponseEntity<Map<String, String>> generateAddress(@RequestBody TransactionRequest request) {
        logger.info("cliPath: " + cliPath);
        logger.info("socketPath: " + socketPath);
        CardanoJTransaction cardanoJTransaction = new CardanoJTransaction();
        Map<String, String> response = new HashMap<>();
        try {
            cardanoJTransaction.buildTransaction(cliPath, socketPath, request.getResourcePath(), request.getSenderAddress(), request.getReceiverAddress(),
                request.getNetwork(), request.getNetworkId(), request.getLovelaceAmount(), request.getSenderName(), "{}");

            cardanoJTransaction.signTransaction(cliPath, request.getResourcePath(), request.getNetwork(), request.getNetworkId(), request.getSenderName());

            String res = cardanoJTransaction.submitTransaction(cliPath, socketPath, request.getResourcePath(), request.getNetwork(), request.getNetworkId(), request.getSenderName());

            response.put("content", res);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error generating address", e);
            response.put("content", "Transaction Failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
