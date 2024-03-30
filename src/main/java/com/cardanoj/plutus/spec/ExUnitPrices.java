package com.cardanoj.plutus.spec;

import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.RationalNumber;
import com.cardanoj.exception.CborDeserializationException;
import com.cardanoj.exception.CborSerializationException;
import com.cardanoj.spec.UnitInterval;
import lombok.*;

import java.util.List;

import static com.cardanoj.common.cbor.CborSerializationUtil.bigIntegerToDataItem;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class ExUnitPrices {
    private UnitInterval memPrice;
    private UnitInterval stepPrice;

    public DataItem serialize() throws CborSerializationException {
        Array array = new Array();

        try {
            RationalNumber memPriceRN = new RationalNumber(bigIntegerToDataItem(memPrice.getNumerator()),
                    bigIntegerToDataItem(memPrice.getDenominator()));
            RationalNumber stepPriceRN = new RationalNumber(bigIntegerToDataItem(stepPrice.getNumerator()),
                    bigIntegerToDataItem(stepPrice.getDenominator()));

            array.add(memPriceRN);
            array.add(stepPriceRN);
        } catch (Exception e) {
            throw new CborSerializationException("ExUnitPrices serialization error", e);
        }

        return array;
    }

    public static ExUnitPrices deserialize(DataItem di) throws CborDeserializationException {
        List<DataItem> dataItemList = ((Array) di).getDataItems();

        if (dataItemList.size() < 2) {
            throw new CborDeserializationException("Invalid no of items. Expected : 2, found : " + dataItemList.size());
        }

        RationalNumber memPriceRN = (RationalNumber) dataItemList.get(0);
        RationalNumber stepPriceRN = (RationalNumber) dataItemList.get(1);

        UnitInterval memPrice = new UnitInterval(memPriceRN.getNumerator().getValue(), memPriceRN.getDenominator().getValue());
        UnitInterval stepPrice = new UnitInterval(stepPriceRN.getNumerator().getValue(), stepPriceRN.getDenominator().getValue());

        return new ExUnitPrices(memPrice, stepPrice);
    }
}
