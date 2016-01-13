package com.etsy.onsite;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Solution {

    private static final Map<Integer, String> map = new LinkedHashMap<>();

    static {
        map.put(500, "D");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    public String int2roman(int number) {
        String result = "";

        for (Entry<Integer, String> entry : map.entrySet()) {
            int digitValue = entry.getKey();
            while (digitValue <= number) {
                result += entry.getValue();
                number -= digitValue;
            }
        }

        return result;
    }
}
