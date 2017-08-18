package com.syt.collection;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by Think on 2017/8/14.
 */
public class TreeMapDemo {
    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<>();
        map.put("1", "CC");
        map.put("6", "CC");
        map.put("4", "CC");
        map.put("5", "CC");

        Set<Map.Entry<String, String>> entrySets =  map.entrySet();
        Iterator iter = entrySets.iterator();
        iter.forEachRemaining(new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println(o);
            }
        });
    }
}
