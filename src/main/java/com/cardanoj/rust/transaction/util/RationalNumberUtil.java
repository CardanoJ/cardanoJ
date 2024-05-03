package com.cardanoj.rust.transaction.util;

import co.nstant.in.cbor.model.RationalNumber;
import com.cardanoj.rust.spec.Rational;
import com.cardanoj.rust.spec.UnitInterval;

import static com.cardanoj.rust.common.cbor.CborSerializationUtil.getBigInteger;

public class RationalNumberUtil {

    /**
     * Convert a RationalNumber to {@link Rational}
     *
     * @param rn
     * @return
     */
    public static Rational toRational(RationalNumber rn) {
        return new Rational(getBigInteger(rn.getNumerator()), getBigInteger(rn.getDenominator()));
    }

    /**
     * Convert a RationalNumber to {@link UnitInterval}
     *
     * @param rn
     * @return
     */
    public static UnitInterval toUnitInterval(RationalNumber rn) {
        return new UnitInterval(getBigInteger(rn.getNumerator()), getBigInteger(rn.getDenominator()));
    }
}
