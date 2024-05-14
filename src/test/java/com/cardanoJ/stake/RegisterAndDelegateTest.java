package com.cardanoJ.stake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.cardanoJ.transaction.Constant.*;

class RegisterAndDelegateTest {

    @Test
    void stakeAddress() {
        RegisterAndDelegate registerAndDelegate = new RegisterAndDelegate();
        assertEquals("src/main/resources/assets/user1scriptstake.addr", registerAndDelegate.stakeAddress(CLI_PATH));
    }

    @Test
    void stakeAddressBuild() {
        RegisterAndDelegate registerAndDelegate = new RegisterAndDelegate();
        assertEquals("src/main/resources/assets/user1script.addr",registerAndDelegate.stakeAddressBuild(CLI_PATH));
    }

    @Test
    void stakeAddressRegCert() {
        RegisterAndDelegate registerAndDelegate = new RegisterAndDelegate();
        assertEquals("src/main/resources/assets/registration.cert", registerAndDelegate.stakeAddressRegCert(CLI_PATH));

    }

    @Test
    void stakeAddressDelCert() {
        RegisterAndDelegate registerAndDelegate = new RegisterAndDelegate();
        assertEquals("src/main/resources/assets/delegation.cert", registerAndDelegate.stakeAddressDelCert(CLI_PATH,"pool1qqa8tkycj4zck4sy7n8mqr22x5g7tvm8hnp9st95wmuvvtw28th"));

    }

    @Test
    void queryProtocolParam() {
        RegisterAndDelegate registerAndDelegate = new RegisterAndDelegate();
        assertEquals("src/main/resources/assets/protocol-parameters.json", registerAndDelegate.queryProtocolParam(CLI_PATH,SOCKET_PATH));

    }

    @Test
    void buildTransaction() {
        RegisterAndDelegate registerAndDelegate = new RegisterAndDelegate();
        assertEquals("src/main/resources/assets/tx.txbody", registerAndDelegate.buildTransaction(CLI_PATH,"41f479dbfb62f62b79643389eb85d2807217f6b26c202ea7035682d05a757842",SOCKET_PATH));

    }

    @Test
    void signTransaction() {
        RegisterAndDelegate registerAndDelegate = new RegisterAndDelegate();
        assertEquals("src/main/resources/assets/tx.signed", registerAndDelegate.signTransaction(CLI_PATH,SOCKET_PATH));

    }

}