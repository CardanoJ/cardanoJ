package com.cardanoJ.script;
import com.cardanoJ.transaction.CardanoJBuildTransaction;
import org.junit.jupiter.api.Test;

import static com.cardanoJ.transaction.CardanoJConstant.*;
import static org.junit.jupiter.api.Assertions.*;

class CardanoJCardanoJTransactionMakeTest {

    @Test
    void addressBuild() {
        CardanoJTransactionMake cardanoJTransactionMake = new CardanoJTransactionMake();
        assertEquals("addr_test1wpnlxv2xv9a9ucvnvzqakwepzl9ltx7jzgm53av2e9ncv4sysemm8", cardanoJTransactionMake.addressBuild(CLI_PATH,getResourcePath(),"--testnet-magic"));
    }

    @Test
    void datumHashFromValue() {
        CardanoJTransactionMake cardanoJTransactionMake = new CardanoJTransactionMake();
        assertEquals("9e478573ab81ea7a8e31891ce0648b81229f408d596a3483e6f4f9b92d3cf710", cardanoJTransactionMake.datumHashFromValue(CLI_PATH,getResourcePath(),"--testnet-magic","6666"));
    }


    @Test
    void signTransaction() {
        CardanoJTransactionMake cardanoJTransactionMake = new CardanoJTransactionMake();
        assertEquals("src/main/resources/assets/addr_test1wpnlxv2xv9a9ucvnvzqakwepzl9ltx7jzgm53av2e9ncv4sysemm8.signed", cardanoJTransactionMake.signTransaction(CLI_PATH,"src/main/resources/assets/","--testnet-magic","2","sender","addr_test1wpnlxv2xv9a9ucvnvzqakwepzl9ltx7jzgm53av2e9ncv4sysemm8"));
    }

    @Test
    void buildTransaction() {
        CardanoJTransactionMake cardanoJTransactionMake = new CardanoJTransactionMake();
        assertEquals("src/main/resources/assets/addr_test1wpnlxv2xv9a9ucvnvzqakwepzl9ltx7jzgm53av2e9ncv4sysemm8.build", cardanoJTransactionMake.buildTransaction(CLI_PATH,getResourcePath(),"addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2","addr_test1wpnlxv2xv9a9ucvnvzqakwepzl9ltx7jzgm53av2e9ncv4sysemm8","--testnet-magic","2",100000000,"sender","9e478573ab81ea7a8e31891ce0648b81229f408d596a3483e6f4f9b92d3cf710"));
    }

    private static String getResourcePath() {
        return CardanoJBuildTransaction.class.getClassLoader().getResource("").getPath();
    }

}