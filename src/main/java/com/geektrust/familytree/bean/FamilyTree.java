package com.geektrust.familytree.bean;

import com.geektrust.familytree.util.CommonConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the family tree.
 */
public class FamilyTree {
    private final FamilyMember root;

    /**
     * Creates a new family tree
     *
     * @param name   name of the root of the family tree
     * @param gender gender of the root of the family tree
     */
    public FamilyTree(String name, Gender gender) {
        this.root = new FamilyMember(name, gender, null, null);
    }

    /**
     * Add spouse to a family member
     *
     * @param memberName name of the current family member
     * @param spouseName name of the spouse
     * @param gender     gender of the spouse
     */
    public void addSpouse(String memberName, String spouseName, Gender gender) {
        FamilyMember member = getMember(this.root, memberName);
        if (member != null) {
            FamilyMember spouse = new FamilyMember(spouseName, gender, null, null);
            spouse.setSpouse(member);
            member.setSpouse(spouse);
        }
    }

    /**
     * Adds a child to a given member of the family
     *
     * <b>Note: </b> a child can be only added to a Female member
     *
     * @param memberName name of the current family member
     * @param childName  name of the child to be added
     * @param gender     gender of the child to be added
     * @return {@code CHILD_ADDED} if successfully added and {@code CHILD_ADDITION_FAILED} if unable to add
     * the child
     */
    public String addChild(String memberName, String childName, Gender gender) {
        if (StringUtils.isEmpty(memberName)) {
            return CommonConstants.CHILD_ADDITION_FAILED;
        }
        FamilyMember member = this.getMember(this.root, memberName);

        if (member == null) {
            return CommonConstants.PERSON_NOT_FOUND;
        } else if (member.getGender().equals(Gender.FEMALE)) {
            FamilyMember child = new FamilyMember(childName, gender, member, member.getSpouse());
            if (member.addChild(child)) {
                return CommonConstants.CHILD_ADDED;
            } else {
                return CommonConstants.CHILD_ADDITION_FAILED;
            }
        }
        return CommonConstants.CHILD_ADDITION_FAILED;
    }

    /**
     * Returns a space separated string of relations of a given {@link Relationship}
     *
     * @param memberName name of the current family member
     * @param relationship relationship of the relations to retrieve
     * @return space separated names of relations or {@code NONE} if no relations exist
     */
    public String getRelationship(String memberName, Relationship relationship) {
        FamilyMember member = this.getMember(this.root, memberName);
        List<FamilyMember> result;
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

    /**
     * Returns the {@link FamilyMember} object of a given family member
     *
     * @param name name of the family member to retrieve
     * @return {@link FamilyMember} of a given name or {@code null} if not exists
     */
    public FamilyMember getMember(String name) {
        return this.getMember(this.root, name);
    }

    /**
     * Searches for the family member of a given name within the family tree
     *
     * @param root root of the family tree
     * @param name name of the member to search
     * @return {@link FamilyMember}
     */
    private FamilyMember getMember(FamilyMember root, String name) {
        if (root == null || name == null) {
            return null;
        }

        if (root.getName().equals(name)) {
            return root;
        } else if (root.getSpouse() != null && root.getSpouse().getName().equals(name)) {
            return root.getSpouse();
        }

        /*
        As male members doesn't have children, set spouse of the member if exists.
        If spouse is null, break the recursion tree by returning null
         */
        if (Gender.MALE.equals(root.getGender())) {
            if (root.getSpouse() != null) {
                root = root.getSpouse();
            } else {
                return null;
            }
        }

        /*
        Recursively search for the member
         */
        FamilyMember member = null;
        for (FamilyMember m : root.getChildren()) {
            member = getMember(m, name);
            if (member != null) {
                break;
            }
        }
        return member;
    }

    public FamilyMember getRoot() {
        return root;
    }
}
