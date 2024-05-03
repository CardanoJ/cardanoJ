package com.cardanoj.rust.common.model;


import com.sun.jna.Structure;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Network {

    public static class ByReference extends Network implements Structure.ByReference {
        public ByReference(int networkId, long protocolMagic) {
            super(networkId, protocolMagic);
        }
    }

    private final int networkId;
    private final long protocolMagic;

    public Network(int networkId, long protocolMagic) {
        this.networkId = networkId;
        this.protocolMagic = protocolMagic;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Network network = (Network)o;
            return this.networkId == network.networkId && this.protocolMagic == network.protocolMagic;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), this.networkId, this.protocolMagic);
    }

    public String toString() {
        return "Network{network_id=" + this.networkId + ", protocol_magic=" + this.protocolMagic + "}";
    }
}

