package com.cardanoj.common.model;


import java.util.Objects;

public class Network {
    private final int networkId;
    private final long protocolMagic;

    public Network(int networkId, long protocolMagic) {
        this.networkId = networkId;
        this.protocolMagic = protocolMagic;
    }

    public int getNetworkId() {
        return this.networkId;
    }

    public long getProtocolMagic() {
        return this.protocolMagic;
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

