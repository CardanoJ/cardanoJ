package com.cardanoJ.stake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static com.cardanoJ.transaction.Constant.*;

class WithdrawUserTest {

    @Test
    void buildTransaction() {
        WithdrawUser withdrawUser = new WithdrawUser();
        assertEquals("src/main/resources/assets/withdrawTx.txbody", withdrawUser.buildTransaction(CLI_PATH,"41f479dbfb62f62b79643389eb85d2807217f6b26c202ea7035682d05a757842#0",SOCKET_PATH));
    }

    @Test
    void signTransaction() {
        WithdrawUser withdrawUser = new WithdrawUser();
        assertEquals("src/main/resources/assets/withdrawTx.signed", withdrawUser.signTransaction(CLI_PATH,SOCKET_PATH));
    }
}