package com.cardanoj.annotation.model;

import com.cardanoj.plutus.annotation.Constr;
import lombok.Data;

import java.util.Optional;

@Data
@Constr
public class BasicModel {
    private Integer i;
    private String s;
    private byte[] b;
    private java.util.List<String> l;
    private java.util.Map<String, java.math.BigInteger> m;
    private Boolean bool;
    private Optional<String> opt;
}
