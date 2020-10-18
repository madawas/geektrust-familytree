package com.geektrust.familytree.util;

import com.geektrust.familytree.bean.FamilyTree;
import com.geektrust.familytree.bean.Gender;
import com.geektrust.familytree.bean.Relationship;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CommonUtil {
    private static int familyIndex = 0;

    private CommonUtil() {

    }

    public static int getFamilyIndex() {
        return ++familyIndex;
    }

    public static FamilyTree initExistingFamily() {
        FamilyTree familyTree = new FamilyTree("Arthur", Gender.MALE);
        familyTree.addSpouse("Arthur", "Margaret", Gender.FEMALE);
        familyTree.addChild("Margaret", "Bill", Gender.MALE);
        familyTree.addChild("Margaret", "Charlie", Gender.MALE);
        familyTree.addChild("Margaret", "Percy", Gender.MALE);
        familyTree.addChild("Margaret", "Ronald", Gender.MALE);
        familyTree.addChild("Margaret", "Ginerva", Gender.FEMALE);

        familyTree.addSpouse("Bill", "Flora", Gender.FEMALE);
        familyTree.addSpouse("Percy", "Audrey", Gender.FEMALE);
        familyTree.addSpouse("Ronald", "Helen", Gender.FEMALE);
        familyTree.addSpouse("Ginerva", "Harry", Gender.MALE);

        familyTree.addChild("Flora", "Victoire", Gender.FEMALE);
        familyTree.addChild("Flora", "Dominique", Gender.FEMALE);
        familyTree.addChild("Flora", "Louis", Gender.MALE);

        familyTree.addSpouse("Victoire", "Ted", Gender.MALE);
        familyTree.addChild("Victoire", "Remus", Gender.MALE);

        familyTree.addChild("Audrey", "Molly", Gender.FEMALE);
        familyTree.addChild("Audrey", "Lucy", Gender.FEMALE);

        familyTree.addChild("Helen", "Rose", Gender.FEMALE);
        familyTree.addChild("Helen", "Hugo", Gender.MALE);

        familyTree.addSpouse("Rose", "Malfoy", Gender.MALE);
        familyTree.addChild("Rose", "Draco", Gender.MALE);
        familyTree.addChild("Rose", "Aster", Gender.FEMALE);

        familyTree.addChild("Ginerva", "James", Gender.MALE);
        familyTree.addChild("Ginerva", "Albus", Gender.MALE);
        familyTree.addChild("Ginerva", "Lily", Gender.FEMALE);

        familyTree.addSpouse("James", "Darcy", Gender.FEMALE);
        familyTree.addSpouse("Albus", "Alice", Gender.FEMALE);

        familyTree.addChild("Darcy", "William", Gender.MALE);
        familyTree.addChild("Alice", "Ron", Gender.MALE);
        familyTree.addChild("Alice", "Ginny", Gender.FEMALE);

        return familyTree;
    }

    public static void processInput(String path, FamilyTree familyTree) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String currentLine = br.readLine();
            while(currentLine != null) {
                String[] params = currentLine.split(" ");
                if (CommonConstants.ADD_CHILD_OPERATION.equals(params[0])) {
                    CommonUtil.processAddChild(familyTree, params);
                } else if (CommonConstants.GET_RELATIONSHIP_OPERATION.equals(params[0])) {
                    CommonUtil.processGetRelationship(familyTree, params);
                }
                currentLine = br.readLine();
            }
        }
    }

    private static void processGetRelationship(FamilyTree familyTree, String[] params) {
        if (params.length < 3) {
            System.out.println(CommonConstants.INVALID_COMMAND);
            return;
        }
        System.out.println(familyTree.getRelationship(params[1], Relationship.get(params[2])));
    }

    private static void processAddChild(FamilyTree familyTree, String[] params) {
        if (params.length < 4) {
            System.out.println(CommonConstants.INVALID_COMMAND);
            return;
        }
        System.out.println(familyTree.addChild(params[1], params[2], Gender.valueOf(params[3].toUpperCase())));
    }

}
