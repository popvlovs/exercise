package com.syt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Think on 2017/8/8.
 */
class Annoyance extends Exception {}
class Sneeze extends Annoyance {}

class ExceptionTestMain {

    public static void main(String[] args)
            throws Exception {
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());

        try {
            try {
                throw new Sneeze();
            }
            catch ( Annoyance a ) {
                System.out.println("Caught Annoyance");
                throw a;
            }
        }
        catch ( Sneeze s ) {
            System.out.println("Caught Sneeze");
            return ;
        }
        finally {
            System.out.println("Hello World!");
        }
    }
}