package com.geektrust.familytree.util;

public class CommonUtil {
    private static int familyIndex = 0;

    private CommonUtil(){

    }

    public static int getFamilyIndex() {
        return ++familyIndex;
    }

    public static boolean isEmpty(String value) {
        return value == null || value.equals("");
    }
}
