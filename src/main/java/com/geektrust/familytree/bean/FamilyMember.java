package com.geektrust.familytree.bean;

import com.geektrust.familytree.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FamilyMember implements Comparable<FamilyMember> {
    private final int index;
    private String name;
    private Gender gender;
    private FamilyMember spouse;
    private FamilyMember mother;
    private FamilyMember father;
    private List<FamilyMember> children;

    public FamilyMember(String name, Gender gender, FamilyMember mother, FamilyMember father) {
        this.index = CommonUtil.getFamilyIndex();
        this.name = name;
        this.gender = gender;
        this.mother = mother;
        this.father = father;
        this.children = new ArrayList<>();
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

    @Override
    public int compareTo(FamilyMember o) {
        return Integer.compare(this.index, o.getIndex());
    }

    public boolean addChild(FamilyMember child) {
        return this.children.add(child);
    }

    public void addSpouse(FamilyMember spouse) {
        this.spouse = spouse;
    }

    public List<FamilyMember> getSiblings() {
        if (this.mother != null) {
            return this.mother.getChildren().stream().filter(child -> !child.getName().equals(this.name)).collect(
                    Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public List<FamilyMember> getSiblings(Gender gender) {
        return this.getSiblings().stream().filter(sibling -> sibling.getGender().equals(gender)).collect(
                Collectors.toList());
    }

    public List<FamilyMember> getChildren(Gender gender) {
        return this.children.stream().filter(child -> child.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<FamilyMember> getMaternalAuntOrUncles(Gender gender) {
        return getAuntOrUncles(this.mother, gender);
    }

    public List<FamilyMember> getPaternalAuntOrUncles(Gender gender) {
        return getAuntOrUncles(this.father, gender);
    }

    private List<FamilyMember> getAuntOrUncles(FamilyMember parent, Gender gender) {
        if (parent != null) {
            return parent.getSiblings().stream().filter(sibling -> sibling.getGender().equals(gender)).collect(
                    Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

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
        List<FamilyMember> spousesSisters = this.getSpouse().getSiblings(Gender.FEMALE);
        List<FamilyMember> wivesOfSiblings = this.getSiblings(Gender.MALE).stream().map(FamilyMember::getSpouse)
                .collect(Collectors.toList());
        spousesSisters.addAll(wivesOfSiblings);
        return spousesSisters;
    }

    private List<FamilyMember> getBrotherInLaws() {
        List<FamilyMember> spousesSisters = this.getSpouse().getSiblings(Gender.MALE);
        List<FamilyMember> wivesOfSiblings = this.getSiblings(Gender.FEMALE).stream().map(FamilyMember::getSpouse)
                .collect(Collectors.toList());
        spousesSisters.addAll(wivesOfSiblings);
        return spousesSisters;
    }
}
