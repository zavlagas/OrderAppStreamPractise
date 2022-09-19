package com.speedorder.orderapp.util;

import java.util.Map;
import java.util.function.Supplier;

public class OptionalMap<T extends Map<?, ?>> {


    private final T map;

    private OptionalMap(T map) {
        this.map = map;
    }


    public static <T extends Map<?, ?>> OptionalMap<T> of(T collection) {
        return new OptionalMap<>(collection);
    }


    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (map != null && !map.isEmpty()) {
            return map;
        } else {
            throw exceptionSupplier.get();
        }
    }
}
