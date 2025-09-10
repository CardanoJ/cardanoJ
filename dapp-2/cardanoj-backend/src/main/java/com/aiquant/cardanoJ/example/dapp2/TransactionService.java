package com.aiquant.cardanoJ.example.dapp2;

import com.cardanoJ.transaction.CardanoJTransaction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransactionService {

//    @Async
//    public CompletableFuture<String> runTransaction(TransactionRequest request, String cliPath, String socketPath) {
//        CardanoJTransaction tx = new CardanoJTransaction();
//        tx.buildTransaction(cliPath, socketPath, ...);
//        tx.signTransaction(cliPath, ...);
//        return CompletableFuture.completedFuture(
//                tx.submitTransaction(cliPath, socketPath, ...)
//        );
//    }

}
