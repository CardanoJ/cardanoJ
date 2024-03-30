package com.cardanoj.transaction.spec;

import com.cardanoj.crypto.SecretKey;
import com.cardanoj.exception.CborSerializationException;
import com.cardanoj.transaction.spec.script.NativeScript;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Policy {

    //Optional - name distinguishes between multiple Policies in a project
    private String name;
    private NativeScript policyScript;
    private List<SecretKey> policyKeys;

    public Policy(String name, NativeScript policyScript) {
        this.name = name;
        this.policyScript = policyScript;
    }

    public Policy(NativeScript policyScript) {
        this.policyScript = policyScript;
    }

    public Policy(NativeScript policyScript, List<SecretKey> policyKeys) {
        this.policyScript = policyScript;
        this.policyKeys = policyKeys;
    }

    public Policy addKey(SecretKey key) {
        if (policyKeys == null) {
            policyKeys = new ArrayList<>();
        }
        policyKeys.add(key);
        return this;
    }

    public String getPolicyId() throws CborSerializationException {
        return policyScript.getPolicyId();
    }
}
