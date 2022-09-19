package com.speedorder.orderapp.util;

import java.util.Collection;
import java.util.function.Supplier;

public final class OptionalCollection<T extends Collection<?>> {

    private final T collection;

    private OptionalCollection(T collection) {
        this.collection = collection;
    }


    public static <T extends Collection<?>> OptionalCollection<T> of(T collection) {
        return new OptionalCollection<>(collection);
    }


    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (collection != null && !collection.isEmpty()) {
            return collection;
        } else {
            throw exceptionSupplier.get();
        }
    }


}
