package com.cardanoj.transaction.spec.cert;

import com.cardanoj.crypto.Bech32;
import com.cardanoj.crypto.Blake2bUtil;
import com.cardanoj.crypto.VerificationKey;
import com.cardanoj.util.HexUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Objects;

@Getter
public class StakePoolId {
    private static final String POOL = "pool";

    private final byte[] poolKeyHash;

    public StakePoolId(byte[] poolKeyHash) {
        this.poolKeyHash = poolKeyHash;
    }

    public static StakePoolId fromHexPoolId(String poolId) {
        byte[] poolIdBytes = HexUtil.decodeHexString(poolId);
        return new StakePoolId(poolIdBytes);
    }

    public static StakePoolId fromBech32PoolId(String poolId) {
        byte[] poolIdBytes = Bech32.decode(poolId).data;
        return new StakePoolId(poolIdBytes);
    }

    public static StakePoolId fromColdVKey(VerificationKey coldVKey) {
        byte[] poolKeyHash = Blake2bUtil.blake2bHash224(coldVKey.getBytes());
        return new StakePoolId(poolKeyHash);
    }

    @JsonIgnore
    public String getBech32PoolId() {
        Objects.requireNonNull(poolKeyHash, "Pool key hash is empty");
        return Bech32.encode(poolKeyHash, POOL);
    }
}
