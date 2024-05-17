package com.cardanoJ.stake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static com.cardanoJ.transaction.CardanoJConstant.*;

class CardanoJWithdrawUserTest {

    @Test
    void buildTransaction() {
        CardanoJWithdrawUser cardanoJWithdrawUser = new CardanoJWithdrawUser();
        assertEquals("src/main/resources/assets/withdrawTx.txbody", cardanoJWithdrawUser.buildTransaction(CLI_PATH,"41f479dbfb62f62b79643389eb85d2807217f6b26c202ea7035682d05a757842#0",SOCKET_PATH));
    }

    @Test
    void signTransaction() {
        CardanoJWithdrawUser cardanoJWithdrawUser = new CardanoJWithdrawUser();
        assertEquals("src/main/resources/assets/withdrawTx.signed", cardanoJWithdrawUser.signTransaction(CLI_PATH,SOCKET_PATH));
    }
}