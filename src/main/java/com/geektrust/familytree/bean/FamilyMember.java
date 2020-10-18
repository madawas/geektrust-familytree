package com.geektrust.familytree.bean;

import com.geektrust.familytree.util.CommonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a member in the family. {@link FamilyMember} implements {@link Comparable} and the
 * comparison is based on the <code>index</code> field which denotes the order which the member added to the family.
 * <p>
 * E.g. FamilyMember with index 1 is added to the family before the FamilyMember with index 2. Hence in a sorting the
 * FamilyMember with index 1 comes first.
 */
public class FamilyMember implements Comparable<FamilyMember> {
    /**
     * {@code index} denotes the order of the family member added
     */
    private final int index;

    /**
     * Name of the family member
     */
    private String name;

    /**
     * Gender of the family member
     *
     * @see Gender
     */
    private Gender gender;

    /**
     * Spouse of the family member
     */
    private FamilyMember spouse;

    /**
     * Mother of the family member
     */
    private FamilyMember mother;

    /**
     * Father of the family member
     */
    private FamilyMember father;

    /**
     * Children of the family member.
     *
     * <b>Note:</b> Only members who are Female will have children. Therefore this field is initialized to
     * {@code null} for Male members.
     */
    private List<FamilyMember> children;

    /**
     * Creates a {@link FamilyMember}
     *
     * @param name   name of the member
     * @param gender gender of the member
     * @param mother mother of the member
     * @param father father of the member
     */
    public FamilyMember(String name, Gender gender, FamilyMember mother, FamilyMember father) {
        this.index = CommonUtil.getFamilyIndex();
        this.name = name;
        this.gender = gender;
        this.mother = mother;
        this.father = father;
        this.children = Gender.MALE.equals(gender) ? null: new ArrayList<>();
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public FamilyMember getSpouse() {
        return spouse;
    }

    public void setSpouse(FamilyMember spouse) {
        this.spouse = spouse;
    }

    public FamilyMember getMother() {
        return mother;
    }

    public void setMother(FamilyMember mother) {
        this.mother = mother;
    }

    public FamilyMember getFather() {
        return father;
    }

    public void setFather(FamilyMember father) {
        this.father = father;
    }

    public List<FamilyMember> getChildren() {
        return children;
    }

    public void setChildren(List<FamilyMember> children) {
        this.children = children;
    }

    /**
     * Compare two family members
     *
     * @param o family member to be compared
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(FamilyMember o) {
        return Integer.compare(this.index, o.getIndex());
    }

    /**
     * Add a child to this family member
     *
     * @param child child to be added
     * @return {@code true} if child added successfully
     */
    public boolean addChild(FamilyMember child) {
        if (Gender.MALE.equals(this.gender)) {
            return false;
        }
        return this.children.add(child);
    }

    /**
     * Returns a list of siblings of this member
     *
     * @return a {@link List} of {@link FamilyMember}s
     */
    public List<FamilyMember> getSiblings() {
        if (this.mother != null) {
            return this.mother.getChildren().stream().filter(child -> !child.getName().equals(this.name)).sorted()
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Returns a list of siblings in a given gender of this member
     *
     * @param gender gender of the siblings to be returned
     * @return a {@link List} of {@link FamilyMember}s
     */
    public List<FamilyMember> getSiblings(Gender gender) {
        return this.getSiblings().stream().filter(sibling -> sibling.getGender().equals(gender)).sorted().collect(
                Collectors.toList());
    }

    /**
     * Returns a list of children in a given gender of this member
     *
     * @param gender gender of the siblings to be returned
     * @return a {@link List} of {@link FamilyMember}s
     */
    public List<FamilyMember> getChildren(Gender gender) {
        if (Gender.MALE.equals(this.gender)) {
            return new ArrayList<>();
        }
        return this.children.stream().filter(child -> child.getGender().equals(gender)).sorted().collect(
                Collectors.toList());
    }

    /**
     * Returns a list of maternal aunts/uncles of this member
     *
     * @param gender {@code Gender.FEMALE} if aunts and {@code Gender.MALE} if uncles
     * @return a {@link List} of {@link FamilyMember}s
     */
    public List<FamilyMember> getMaternalAuntOrUncles(Gender gender) {
        return getAuntOrUncles(this.mother, gender);
    }

    /**
     * Returns a list of paternal aunts/uncles of this member
     *
     * @param gender {@code Gender.FEMALE} if aunts and {@code Gender.MALE} if uncles
     * @return a {@link List} of {@link FamilyMember}s
     */
    public List<FamilyMember> getPaternalAuntOrUncles(Gender gender) {
        return this.getAuntOrUncles(this.father, gender);
    }

    private List<FamilyMember> getAuntOrUncles(FamilyMember parent, Gender gender) {
        if (parent != null) {
            return parent.getSiblings().stream().filter(sibling -> sibling.getGender().equals(gender)).sorted().collect(
                    Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Returns a list of in-laws of this member
     *
     * @param gender {@code Gender.FEMALE} if sister-in-laws and {@code Gender.MALE} if brother-in-laws
     * @return a {@link List} of {@link FamilyMember}s
     */
    public List<FamilyMember> getInLaws(Gender gender) {
        if (gender.equals(Gender.FEMALE)) {
            return this.getSisterInLaws();
        } else if (gender.equals(Gender.MALE)) {
            return this.getBrotherInLaws();
        } else {
            return new ArrayList<>();
        }
    }

    private List<FamilyMember> getSisterInLaws() {
        List<FamilyMember> spousesSisters = this.spouse != null ? this.spouse.getSiblings(
                Gender.FEMALE): new ArrayList<>();
        List<FamilyMember> wivesOfSiblings = this.getSiblings(Gender.MALE).stream().map(FamilyMember::getSpouse)
                .collect(Collectors.toList());
        spousesSisters.addAll(wivesOfSiblings);
        // Sorting the relations to maintain the order of insertion
        Collections.sort(spousesSisters);
        return spousesSisters;
    }

    private List<FamilyMember> getBrotherInLaws() {
        List<FamilyMember> spousesSisters = this.spouse != null ? this.spouse.getSiblings(
                Gender.MALE): new ArrayList<>();
        List<FamilyMember> wivesOfSiblings = this.getSiblings(Gender.FEMALE).stream().map(FamilyMember::getSpouse)
                .collect(Collectors.toList());
        spousesSisters.addAll(wivesOfSiblings);
        // Sorting the relations to maintain the order of insertion
        Collections.sort(spousesSisters);
        return spousesSisters;
    }
}
