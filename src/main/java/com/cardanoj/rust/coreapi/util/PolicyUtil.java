package com.cardanoj.rust.coreapi.util;

import com.cardanoj.rust.crypto.KeyGenUtil;
import com.cardanoj.rust.crypto.Keys;
import com.cardanoj.rust.crypto.SecretKey;
import com.cardanoj.rust.crypto.VerificationKey;
import com.cardanoj.rust.exception.CborSerializationException;
import com.cardanoj.rust.transaction.spec.Policy;
import com.cardanoj.rust.transaction.spec.script.RequireTimeBefore;
import com.cardanoj.rust.transaction.spec.script.ScriptAll;
import com.cardanoj.rust.transaction.spec.script.ScriptAtLeast;
import com.cardanoj.rust.transaction.spec.script.ScriptPubkey;
import com.cardanoj.rust.util.Tuple;

import java.util.ArrayList;
import java.util.List;

public class PolicyUtil {

    private static final int SLOTS_PER_EPOCH = 5 * 24 * 60 * 60;

    public static Policy createEpochBasedTimeLockedPolicy(String name, long currentSlot, long epochs) throws CborSerializationException {
        return createEpochBasedTimeLockedPolicy(name, currentSlot, epochs, SLOTS_PER_EPOCH);
    }

    public static Policy createEpochBasedTimeLockedPolicy(String name, long currentSlot, long epochs, Integer slotsPerEpoch) throws CborSerializationException {
        if (slotsPerEpoch == null) {
            slotsPerEpoch = SLOTS_PER_EPOCH;
        }
        Keys keys = KeyGenUtil.generateKey();
        VerificationKey verificationKey = keys.getVkey();
        ScriptPubkey scriptPubkey = ScriptPubkey.create(verificationKey);
        RequireTimeBefore requireTimeBefore = new RequireTimeBefore(currentSlot + slotsPerEpoch * epochs);
        ScriptAll scriptAll = new ScriptAll().addScript(requireTimeBefore).addScript(scriptPubkey);
        return new Policy(name, scriptAll).addKey(keys.getSkey());
    }

    public static Policy createMultiSigScriptAllPolicy(String name, int numOfSigners) throws CborSerializationException {
        if (numOfSigners < 1) {
            throw new IllegalArgumentException("Number of policy signers must be larger or equal to 1");
        }
        ScriptAll scriptAll = new ScriptAll();
        List<SecretKey> policyKeys = new ArrayList<>();
        for (int i = 0; i < numOfSigners; i++) {
            Tuple<ScriptPubkey, Keys> tuple = ScriptPubkey.createWithNewKey();
            scriptAll.addScript(tuple._1);
            policyKeys.add(tuple._2.getSkey());
        }
        return new Policy(name, scriptAll, policyKeys);
    }

    public static Policy createMultiSigScriptAtLeastPolicy(String name, int numOfSigners, int required) throws CborSerializationException {
        if (numOfSigners < 1) {
            throw new IllegalArgumentException("Number of policy signers must be larger or equal to 1");
        }
        if (required > numOfSigners) {
            throw new IllegalArgumentException("Number of required signers cannot be higher than overall signers amount");
        }
        ScriptAtLeast scriptAtLeast = new ScriptAtLeast(required);
        List<SecretKey> policyKeys = new ArrayList<>();
        for (int i = 0; i < numOfSigners; i++) {
            Tuple<ScriptPubkey, Keys> tuple = ScriptPubkey.createWithNewKey();
            scriptAtLeast.addScript(tuple._1);
            policyKeys.add(tuple._2.getSkey());
        }
        return new Policy(name, scriptAtLeast, policyKeys);
    }
}
