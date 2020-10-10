package com.geektrust.familytree.bean;

import java.util.HashMap;
import java.util.Map;

public enum Relationship {
    SON("Son"), DAUGHTER("Daughter"), SIBLINGS("Siblings"), BROTHER_IN_LAW("Brother-In-Law"), SISTER_IN_LAW(
            "Sister-In-Law"), MATERNAL_AUNT("Maternal-Aunt"), PATERNAL_AUNT("Paternal-Aunt"), MATERNAL_UNCLE(
            "Maternal-Uncle"), PATERNAL_UNCLE("Paternal-Uncle");

    private final String value;
    private static final Map<String, Relationship> lookup = new HashMap<>();

    static {
        for (Relationship relationship : Relationship.values()) {
            lookup.put(relationship.getValue(), relationship);
        }
    }

    Relationship(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Relationship get(String value) {
        return lookup.get(value);
    }
}
