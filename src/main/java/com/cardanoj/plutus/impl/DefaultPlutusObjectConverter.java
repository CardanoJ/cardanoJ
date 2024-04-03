package com.cardanoj.plutus.impl;

import com.cardanoj.plutus.annotation.Constr;
import com.cardanoj.plutus.annotation.PlutusField;
import com.cardanoj.plutus.api.PlutusObjectConverter;
import com.cardanoj.plutus.exception.PlutusDataConvertionException;
import com.cardanoj.plutus.spec.BigIntPlutusData;
import com.cardanoj.plutus.spec.BytesPlutusData;
import com.cardanoj.plutus.spec.ConstrPlutusData;
import com.cardanoj.plutus.spec.ListPlutusData;
import com.cardanoj.plutus.spec.MapPlutusData;
import com.cardanoj.plutus.spec.PlutusData;
import com.cardanoj.util.HexUtil;
import com.cardanoj.util.Tuple;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPlutusObjectConverter implements PlutusObjectConverter {
    private static final Logger log = LoggerFactory.getLogger(DefaultPlutusObjectConverter.class);

    public DefaultPlutusObjectConverter() {
    }

    public PlutusData toPlutusData(Object obj) {
        if (Objects.isNull(obj)) {
            throw new PlutusDataConvertionException("Can't convert a null object");
        } else if (obj instanceof PlutusData) {
            return (PlutusData)obj;
        } else if (obj instanceof Integer) {
            return BigIntPlutusData.of((Integer)obj);
        } else if (obj instanceof BigInteger) {
            return BigIntPlutusData.of((BigInteger)obj);
        } else if (obj instanceof Long) {
            return BigIntPlutusData.of((Long)obj);
        } else if (obj instanceof byte[]) {
            return BytesPlutusData.of((byte[])obj);
        } else if (obj instanceof String) {
            return BytesPlutusData.of((String)obj);
        } else if (obj instanceof Optional) {
            return this.convertOptionalType((Optional)obj);
        } else {
            Class<?> clazz = obj.getClass();
            Constr constr = (Constr)clazz.getAnnotation(Constr.class);
            if (constr == null) {
                throw new PlutusDataConvertionException("@Contr annotation not found in class : " + clazz.getName());
            } else {
                int alternative = constr.alternative();
                List fields = (List)Arrays.stream(clazz.getDeclaredFields()).filter((fieldx) -> {
                    return fieldx.getAnnotation(PlutusField.class) != null;
                }).map((fieldx) -> {
                    return new Tuple(fieldx, (PlutusField)fieldx.getDeclaredAnnotation(PlutusField.class));
                }).collect(Collectors.toList());
                ListPlutusData listPlutusData = new ListPlutusData();
                ConstrPlutusData constrPlutusData = ConstrPlutusData.builder().alternative((long)alternative).data(listPlutusData).build();
                Iterator var8 = fields.iterator();

                while(var8.hasNext()) {
                    Tuple<Field, PlutusField> tuple = (Tuple)var8.next();
                    Field field = (Field)tuple._1;
                    field.setAccessible(true);

                    Object value;
                    try {
                        value = field.get(obj);
                    } catch (IllegalAccessException var13) {
                        throw new PlutusDataConvertionException("Unable to convert value for field : " + field.getName());
                    }

                    PlutusData plutusData = this._toPlutusData(field.getName(), value);
                    constrPlutusData.getData().add(plutusData);
                }

                return constrPlutusData;
            }
        }
    }

    private ConstrPlutusData convertOptionalType(Optional<?> obj) {
        return obj.isEmpty() ? ConstrPlutusData.builder().alternative(1L).data(ListPlutusData.of(new PlutusData[0])).build() : ConstrPlutusData.builder().alternative(0L).data(ListPlutusData.of(new PlutusData[]{this.toPlutusData(obj.get())})).build();
    }

    private PlutusData _toPlutusData(String fieldName, Object obj) {
        if (Objects.isNull(obj)) {
            throw new PlutusDataConvertionException("Can't convert a null object. Field : " + fieldName);
        } else {
            Class<?> clazz = obj.getClass();
            PlutusData plutusData = null;
            if (clazz == byte[].class) {
                plutusData = BytesPlutusData.of((byte[])obj);
            } else if (clazz == String.class) {
                String value = (String)obj;
                if (!value.startsWith("0x") && !value.startsWith("0X")) {
                    plutusData = BytesPlutusData.of(value);
                } else {
                    byte[] bytes = HexUtil.decodeHexString(value);
                    plutusData = BytesPlutusData.of(bytes);
                }
            } else if (clazz != BigInteger.class && clazz != Long.class && clazz != Integer.class) {
                Iterator iterator;
                if (Collection.class.isAssignableFrom(clazz)) {
                    Collection values = (Collection)obj;
                    plutusData = new ListPlutusData();
                    iterator = values.iterator();

                    while(iterator.hasNext()) {
                        Object value = iterator.next();
                        ((ListPlutusData)plutusData).add(this._toPlutusData(fieldName + "[x]", value));
                    }
                } else if (Map.class.isAssignableFrom(clazz)) {
                    Map map = (Map)obj;
                    plutusData = new MapPlutusData();
                    iterator = map.entrySet().iterator();

                    while(iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry)iterator.next();
                        PlutusData keyPlutusData = this.toPlutusData(entry.getKey());
                        PlutusData valuePlutusData = this.toPlutusData(entry.getValue());
                        ((MapPlutusData)plutusData).put(keyPlutusData, valuePlutusData);
                    }
                } else {
                    plutusData = this.toPlutusData(obj);
                }
            } else {
                Number value = (Number)obj;
                if (clazz == BigInteger.class) {
                    plutusData = BigIntPlutusData.of((BigInteger)value);
                } else if (clazz == Long.class) {
                    plutusData = BigIntPlutusData.of((Long)value);
                } else if (clazz == Integer.class) {
                    plutusData = BigIntPlutusData.of((Integer)value);
                }
            }

            return (PlutusData)plutusData;
        }
    }
}
