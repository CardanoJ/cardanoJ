package com.cardanoJ.stake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.cardanoJ.transaction.CardanoJConstant.*;

class CardanoJRegisterAndDelegateTest {

    @Test
    void stakeAddress() {
        CardanoJRegisterAndDelegate cardanoJRegisterAndDelegate = new CardanoJRegisterAndDelegate();
        assertEquals("src/main/resources/assets/user1scriptstake.addr", cardanoJRegisterAndDelegate.stakeAddress(CLI_PATH));
    }

    @Test
    void stakeAddressBuild() {
        CardanoJRegisterAndDelegate cardanoJRegisterAndDelegate = new CardanoJRegisterAndDelegate();
        assertEquals("src/main/resources/assets/user1script.addr", cardanoJRegisterAndDelegate.stakeAddressBuild(CLI_PATH));
    }

    @Test
    void stakeAddressRegCert() {
        CardanoJRegisterAndDelegate cardanoJRegisterAndDelegate = new CardanoJRegisterAndDelegate();
        assertEquals("src/main/resources/assets/registration.cert", cardanoJRegisterAndDelegate.stakeAddressRegCert(CLI_PATH));

    }

    @Test
    void stakeAddressDelCert() {
        CardanoJRegisterAndDelegate cardanoJRegisterAndDelegate = new CardanoJRegisterAndDelegate();
        assertEquals("src/main/resources/assets/delegation.cert", cardanoJRegisterAndDelegate.stakeAddressDelCert(CLI_PATH,"pool1qqa8tkycj4zck4sy7n8mqr22x5g7tvm8hnp9st95wmuvvtw28th"));

    }

    @Test
    void queryProtocolParam() {
        CardanoJRegisterAndDelegate cardanoJRegisterAndDelegate = new CardanoJRegisterAndDelegate();
        assertEquals("src/main/resources/assets/protocol-parameters.json", cardanoJRegisterAndDelegate.queryProtocolParam(CLI_PATH,SOCKET_PATH));

    }

    @Test
    void buildTransaction() {
        CardanoJRegisterAndDelegate cardanoJRegisterAndDelegate = new CardanoJRegisterAndDelegate();
        assertEquals("src/main/resources/assets/tx.txbody", cardanoJRegisterAndDelegate.buildTransaction(CLI_PATH,"41f479dbfb62f62b79643389eb85d2807217f6b26c202ea7035682d05a757842",SOCKET_PATH));

    }

    @Test
    void signTransaction() {
        CardanoJRegisterAndDelegate cardanoJRegisterAndDelegate = new CardanoJRegisterAndDelegate();
        assertEquals("src/main/resources/assets/tx.signed", cardanoJRegisterAndDelegate.signTransaction(CLI_PATH,SOCKET_PATH));

    }

}