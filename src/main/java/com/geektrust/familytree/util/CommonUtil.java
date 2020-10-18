package com.geektrust.familytree.util;

import com.geektrust.familytree.bean.FamilyTree;
import com.geektrust.familytree.bean.Gender;
import com.geektrust.familytree.bean.Relationship;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommonUtil {
    private static int familyIndex = 0;

    private CommonUtil() {

    }

    /**
     * Generates a index to the family
     *
     * @return Integer index
     */
    public static int getFamilyIndex() {
        return ++familyIndex;
    }

    /**
     * Initialize the existing family tree
     * @return {@link FamilyTree}
     */
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

    /**
     * Process input file
     *
     * @param path path of the input file
     * @param familyTree {@link FamilyTree} to process the input to
     * @return List of results after processing the input
     * @throws IOException if error occurred while reading the file
     */
    public static List<String> processInput(String path, FamilyTree familyTree) throws IOException {
        List<String> resultList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String currentLine = br.readLine();
            while (currentLine != null) {
                String[] params = currentLine.split(" ");
                if (CommonConstants.ADD_CHILD_OPERATION.equals(params[0])) {
                    if (params.length < 4) {
                        resultList.add(CommonConstants.INVALID_COMMAND);
                    } else {
                        resultList.add(
                                familyTree.addChild(params[1], params[2], Gender.valueOf(params[3].toUpperCase())));
                    }
                } else if (CommonConstants.GET_RELATIONSHIP_OPERATION.equals(params[0])) {
                    if (params.length < 3) {
                        resultList.add(CommonConstants.INVALID_COMMAND);
                    } else {
                        resultList.add(familyTree.getRelationship(params[1], Relationship.get(params[2])));
                    }
                }
                currentLine = br.readLine();
            }
        }
        return resultList;
    }
}
