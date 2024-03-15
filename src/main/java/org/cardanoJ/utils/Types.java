package org.cardanoJ.utils;

import java.math.BigInteger;

public class Types {
    public class DatumHash {
        private String value;

        public DatumHash(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public class Redeemer {
        private String value;

        public Redeemer(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public class Lovelace {
        private BigInteger value;

        public Lovelace(BigInteger value) {
            this.value = value;
        }

        public BigInteger getValue() {
            return value;
        }
    }

    public class Label {
        private int value;

        public Label(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public class TransactionWitnesses {
        private String value;

        public TransactionWitnesses(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public class Transaction {
        private String value;

        public Transaction(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public class PrivateKey {
        private String value;

        public PrivateKey(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public class PublicKey {
        private String value;

        public PublicKey(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public class ScriptRef {
        private String value;

        public ScriptRef(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public class Payload {
        private String value;

        public Payload(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}

