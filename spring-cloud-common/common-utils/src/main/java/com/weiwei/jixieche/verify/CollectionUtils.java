package com.weiwei.jixieche.verify;

import java.util.Collection;

public class CollectionUtils {
    public CollectionUtils() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }
}