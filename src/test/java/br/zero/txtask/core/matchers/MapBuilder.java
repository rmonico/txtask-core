package br.zero.txtask.core.matchers;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<KEYTYPE, VALUETYPE> {

    private Map<KEYTYPE, VALUETYPE> mapInstance;

    public MapBuilder(Class<KEYTYPE> keyType, Class<VALUETYPE> valueType) {
    }

    public MapBuilder<KEYTYPE, VALUETYPE> hashMap() {
        this.mapInstance = new HashMap<KEYTYPE, VALUETYPE>();

        return this;
    }

    public MapBuilder<KEYTYPE, VALUETYPE> put(KEYTYPE key,
            VALUETYPE value) {
        this.mapInstance.put(key, value);

        return this;
    }

    public Map<KEYTYPE, VALUETYPE> done() {
        return mapInstance;
    }

    public static <KEYTYPE, VALUETYPE> MapBuilder<KEYTYPE, VALUETYPE> create(Class<KEYTYPE> keyType,
            Class<VALUETYPE> valueType) {
        return new MapBuilder<KEYTYPE, VALUETYPE>(keyType, valueType);
    }
}
