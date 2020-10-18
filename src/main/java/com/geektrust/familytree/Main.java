package com.geektrust.familytree;

import com.geektrust.familytree.bean.FamilyTree;
import com.geektrust.familytree.util.CommonUtil;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args[0] == null) {
            throw new IllegalArgumentException("Please provide the path to input file");
        }
        FamilyTree familyTree = CommonUtil.initExistingFamily();
        try {
            List<String> resultList = CommonUtil.processInput(args[0], familyTree);
            resultList.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Error occurred while reading the input file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
