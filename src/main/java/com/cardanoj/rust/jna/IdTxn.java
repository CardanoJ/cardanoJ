package com.cardanoj.rust.jna;

import com.cardanoj.rust.common.model.Networks;
import com.cardanoj.rust.coreapi.account.Account;
import com.cardanoj.rust.exception.CborDeserializationException;
import com.cardanoj.rust.exception.CborSerializationException;
import com.cardanoj.rust.transaction.spec.Transaction;
import com.cardanoj.rust.util.HexUtil;

import java.nio.ByteBuffer;

public class IdTxn {
    public static void main(String[] args) throws CborSerializationException, CborDeserializationException {
        Account account = new Account(Networks.testnet());

//        String mnemonic = "";
//        String mainAddr = JNAUtil.getBaseAddressByNetwork(mnemonic, 0, NetworksJNA.mainnet());
//        String testAddr = JNAUtil.getBaseAddressByNetwork(mnemonic, 1, NetworksJNA.testnet());
//        System.out.println("Main Address: " + mainAddr);
//        System.out.println("Test Address: " + testAddr);

        String hexStr = RawTxnHex.rawTxnHexGeneration();

//        byte[] bytes = HexUtil.decodeHexString("6b8d07d69639e9413dd637a1a815a7323c69c86abbafb66dbfdb1aa7");
        byte[] bytes = HexUtil.decodeHexString(hexStr);
        System.out.println("Hex Str Length: " + bytes.length);

        ByteBuffer bb = ByteBuffer.wrap(bytes);

        byte[] policyId = new byte[28];
        byte[] asset = new byte[bytes.length - 28];

        bb.get(policyId, 0, policyId.length);
        bb.get(asset, 0, asset.length);

        System.out.println("Policy ID: " + HexUtil.encodeHexString(policyId));
        System.out.println("Asset: " + HexUtil.encodeHexString(asset));

        String txn = hexStr;
        Transaction txn0;
        try {
            txn0 = Transaction.deserialize(HexUtil.decodeHexString(txn));
        } catch (CborDeserializationException e) {
            throw new RuntimeException(e);
        }
        System.out.println(txn0);
    }
}
