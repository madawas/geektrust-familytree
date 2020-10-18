package com.geektrust.familytree;

import com.geektrust.familytree.bean.FamilyTree;
import com.geektrust.familytree.util.CommonUtil;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        FamilyTree familyTree = CommonUtil.initExistingFamily();
        try {
            CommonUtil.processInput("input.txt", familyTree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
