package com.cardanoj.rust.coreapi.transaction.model;

import com.cardanoj.rust.spec.NetworkId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailsParams {
    private long ttl;
    private long validityStartInterval;
    private NetworkId networkId;
}
