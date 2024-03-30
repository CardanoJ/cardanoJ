package com.cardanoj.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pointer {
    long slot;
    int txIndex;
    int certIndex;
}
