package com.geektrust.familytree.bean;

import com.geektrust.familytree.util.CommonConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class FamilyTree {
    private final FamilyMember root;

    public FamilyTree(String name, Gender gender) {
        this.root = new FamilyMember(name, gender, null, null);
    }

    public void addSpouse(String memberName, String spouseName, Gender gender) {
        FamilyMember member = getMember(this.root, memberName);
        if (member != null) {
            FamilyMember spouse = new FamilyMember(spouseName, gender, null, null);
            spouse.setSpouse(member);
            member.setSpouse(spouse);
        }
    }

    public String addChild(String memberName, String childName, Gender gender) {
        if (StringUtils.isEmpty(memberName)) {
            return CommonConstants.CHILD_ADDITION_FAILED;
        }
        FamilyMember member = this.getMember(this.root, memberName);

        if (member == null) {
            return CommonConstants.PERSON_NOT_FOUND;
        } else if (member.getGender().equals(Gender.FEMALE)) {
            FamilyMember child = new FamilyMember(childName, gender, member, member.getSpouse());
            member.addChild(child);
            return CommonConstants.CHILD_ADDITION_SUCCEEDED;
        }
        return CommonConstants.CHILD_ADDITION_FAILED;
    }

    public String getRelationship(String memberName, Relationship relationship) {
        FamilyMember member = this.getMember(this.root, memberName);
        List<FamilyMember> result = null;
        if (member == null) {
            return CommonConstants.PERSON_NOT_FOUND;
        }

        switch (relationship) {
            case SON:
                result = member.getChildren(Gender.MALE);
                break;
            case DAUGHTER:
                result = member.getChildren(Gender.FEMALE);
                break;
            case SIBLINGS:
                result = member.getSiblings();
                break;
            case MATERNAL_AUNT:
                result = member.getMaternalAuntOrUncles(Gender.FEMALE);
                break;
            case PATERNAL_AUNT:
                result = member.getPaternalAuntOrUncles(Gender.FEMALE);
                break;
            case MATERNAL_UNCLE:
                result = member.getMaternalAuntOrUncles(Gender.MALE);
                break;
            case PATERNAL_UNCLE:
                result = member.getPaternalAuntOrUncles(Gender.MALE);
                break;
            case SISTER_IN_LAW:
                result = member.getInLaws(Gender.FEMALE);
                break;
            case BROTHER_IN_LAW:
                result = member.getInLaws(Gender.MALE);
                break;
            default:
                return CommonConstants.NONE;
        }

        if (result != null && !result.isEmpty()) {
            return result.stream().map(FamilyMember::getName).collect(Collectors.joining(" "));
        } else {
            return CommonConstants.NONE;
        }
    }

    private FamilyMember getMember(FamilyMember root, String name) {
        if (root == null || name == null) {
            return null;
        }

        if (root.getName().equals(name)) {
            return root;
        } else if (root.getSpouse().getName().equals(name)) {
            return root.getSpouse();
        }
        FamilyMember member = null;
        for (FamilyMember m: root.getChildren()) {
            member = getMember(m, name);
            if (member != null) {
                break;
            }
        }
        return member;
    }
}
