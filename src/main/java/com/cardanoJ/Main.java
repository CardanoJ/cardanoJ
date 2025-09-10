package com.cardanoJ;

import com.cardanoJ.address.CardanoJBuildAddress;
import com.cardanoJ.script.CardanoJTransactionCollect;
import com.cardanoJ.script.CardanoJTransactionMake;
import com.cardanoJ.stake.CardanoJQueryUtxo;
import com.cardanoJ.transaction.*;

import java.util.List;
import java.util.Map;

import static com.cardanoJ.transaction.CardanoJConstant.*;

public class Main {
    public static void main(String[] args) {
//        address generation
//        CardanoJBuildAddress cardanoJBuildAddress = new CardanoJBuildAddress();
//        String resourcePath = cardanoJBuildAddress.addressGen(CLI_PATH, "src/main/resources/assets/", "snehasish1", TESTNET, TESTNET_MAGIC_NUMBER);
//        System.out.println("address generated: " + resourcePath);

//        query address balance
//        CardanoJQueryAddress cardanoJQueryAddress = new CardanoJQueryAddress();
//        long balance = cardanoJQueryAddress.queryAddress(CLI_PATH, SOCKET_PATH, "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p", TESTNET, TESTNET_MAGIC_NUMBER);
//        System.out.println("balance: " + balance);

//        query address balance2
//        CardanoJQueryUtxo cardanoJQueryUtxo = new CardanoJQueryUtxo();
//        long balance = cardanoJQueryUtxo.queryAddress(CLI_PATH, SOCKET_PATH, "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p", TESTNET, TESTNET_MAGIC_NUMBER);
//        System.out.println("balance: " + balance);

//        query address utxo
//        CardanoJUtxoQuery cardanoJUtxoQuery = new CardanoJUtxoQuery();
//        List<Map<String, Object>> utxos = cardanoJUtxoQuery.queryUtxos(CLI_PATH, SOCKET_PATH, "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p", TESTNET, TESTNET_MAGIC_NUMBER);
//        System.out.println(utxos);

//        address utxo details
//        CardanoJTransaction cardanoJTransaction = new CardanoJTransaction();
//        String res = cardanoJTransaction.getTransactionDetails(CLI_PATH, SOCKET_PATH, "src/main/resources/assets/", "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p", TESTNET, TESTNET_MAGIC_NUMBER);
//        System.out.println("res: " + res);

//        build transaction
//        CardanoJTransaction cardanoJTransaction = new CardanoJTransaction();
//        String res = cardanoJTransaction.buildTransaction(CLI_PATH, SOCKET_PATH, "src/main/resources/assets/", "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p", "addr_test1qr864nwnz26egxxzwnuex7danrrrkjl4kl4neevssqu8zlwr2ghnx4q6fql7slk9h0wnw8423pd8frap2kycwxf0hjdqwg0gjq",
//                TESTNET, TESTNET_MAGIC_NUMBER, 2000000, "snehasish", "{}");
//        System.out.println("res: " + res);

//        sign transaction
//        String res = cardanoJTransaction.signTransaction(CLI_PATH, "src/main/resources/assets/", TESTNET, TESTNET_MAGIC_NUMBER, "snehasish");
//        System.out.println("res: " + res);

//        submit transaction
//        String res = cardanoJTransaction.submitTransaction(CLI_PATH, SOCKET_PATH, "src/main/resources/assets/", TESTNET, TESTNET_MAGIC_NUMBER, "snehasish");
//        System.out.println("res: " + res);

        // SMART CONTRACT INTERACTION

//        CardanoJCreateDatum cardanoJCreateDatum = new CardanoJCreateDatum();
//        String res = cardanoJCreateDatum.create("addr_test1qr864nwnz26egxxzwnuex7danrrrkjl4kl4neevssqu8zlwr2ghnx4q6fql7slk9h0wnw8423pd8frap2kycwxf0hjdqwg0gjq",
//                "addr_test1qzlwkd2s2ftvj0hdv8fdgg8z590jd9zucy7jeg8puw05x88h6kuugkkdyf9qn0sm36krdqsch8l2vcc7wk6py6ykdngq9l09f8", 5000000, "src/main/resources/assets/");

//        String res = cardanoJCreateDatum.createDatumV2(10, "xyz", "src/main/resources/assets/");

        // script address build
//        CardanoJTransactionMake cardanoJTransactionMake = new CardanoJTransactionMake();
//        String res = cardanoJTransactionMake.addressBuild(CLI_PATH, "src/main/resources/assets/AlwaysSucceeds.plutus", "src/main/resources/assets/", TESTNET, TESTNET_MAGIC_NUMBER);
//        System.out.println("res: " + res);

        // datum hash value
//        String datumHash = cardanoJTransactionMake.datumHashFromValue(CLI_PATH, "{}");

//        build script locking transaction
//        String res = cardanoJTransactionMake.buildTransaction(CLI_PATH, SOCKET_PATH, "src/main/resources/assets/", "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p",
//                "addr_test1wqag3rt979nep9g2wtdwu8mr4gz6m4kjdpp5zp705km8wys6t2kla", TESTNET, TESTNET_MAGIC_NUMBER, 5000000, "src/main/resources/assets/datum.json");

        // sign script locking tranasction
//        String res = cardanoJTransactionMake.signTransaction(CLI_PATH, "src/main/resources/assets/", TESTNET, TESTNET_MAGIC_NUMBER, "snehasish", "addr_test1wqag3rt979nep9g2wtdwu8mr4gz6m4kjdpp5zp705km8wys6t2kla");

        // submit script locking transaction
//        String res = cardanoJTransactionMake.submitTransaction(CLI_PATH, SOCKET_PATH, "src/main/resources/assets/", TESTNET, TESTNET_MAGIC_NUMBER, "addr_test1wqag3rt979nep9g2wtdwu8mr4gz6m4kjdpp5zp705km8wys6t2kla");


        // protocol parameter generation
//        CardanoJTransactionCollect cardanoJTransactionCollect = new CardanoJTransactionCollect();
//        String res = cardanoJTransactionCollect.queryProtocolParam(CLI_PATH, "src/main/resources/assets/", TESTNET, TESTNET_MAGIC_NUMBER, SOCKET_PATH);
//        System.out.println("res: " + res);

        // datum hash (redeemer) for script unlocking transaction
//        String res = cardanoJTransactionCollect.datumHashFromValue(CLI_PATH, "{}");

        // fetch script utxos
//        List<String> res = cardanoJTransactionCollect.fetchUtxos(CLI_PATH, SOCKET_PATH, "src/main/resources/assets/", "addr_test1wqag3rt979nep9g2wtdwu8mr4gz6m4kjdpp5zp705km8wys6t2kla", TESTNET, TESTNET_MAGIC_NUMBER);

        // build script unlock transaction
//        String res = cardanoJTransactionCollect.buildTransaction(CLI_PATH, "src/main/resources/assets/", "addr_test1wqag3rt979nep9g2wtdwu8mr4gz6m4kjdpp5zp705km8wys6t2kla", "d2e985f5e30efef633d54e24c60a9ac30302e5896e3bade388016cb634b570ba", "{}",
//                "{}", "src/main/resources/assets/AlwaysSucceeds.plutus", "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p",
//                "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p", TESTNET, TESTNET_MAGIC_NUMBER, SOCKET_PATH);


        // sign script unlock transaction
//        String res = cardanoJTransactionCollect.signTransaction(CLI_PATH, "src/main/resources/assets/", TESTNET, TESTNET_MAGIC_NUMBER, "src/main/resources/assets/snehasish.skey", "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p");


        // submit script unlock transaction
//        String res = cardanoJTransactionCollect.submitTransaction(CLI_PATH, "src/main/resources/assets/", TESTNET, TESTNET_MAGIC_NUMBER, "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p", SOCKET_PATH);

//        System.out.println("res: " + res);
    }
}
