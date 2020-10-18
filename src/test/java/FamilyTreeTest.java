import com.geektrust.familytree.bean.FamilyMember;
import com.geektrust.familytree.bean.FamilyTree;
import com.geektrust.familytree.bean.Gender;
import com.geektrust.familytree.bean.Relationship;
import com.geektrust.familytree.util.CommonConstants;
import com.geektrust.familytree.util.CommonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FamilyTreeTest {
    private FamilyTree familyTree;

    /**
     * Initialize the existing family tree before test
     */
    @Before
    public void setup() {
        this.familyTree = CommonUtil.initExistingFamily();
    }

    /**
     * Test existing family structure
     */
    @Test
    public void testExistingFamilyOperations() {
        Assert.assertEquals("Arthur", familyTree.getRoot().getName());
        Assert.assertEquals("Margaret", familyTree.getRoot().getSpouse().getName());
        Assert.assertEquals("Bill Charlie Percy Ronald Ginerva",
                familyTree.getRoot().getSpouse().getChildren().stream().map(FamilyMember::getName)
                        .collect(Collectors.joining(" ")));
        Assert.assertNotNull(familyTree.getMember("Hugo"));
        Assert.assertEquals("Helen", familyTree.getMember("Hugo").getMother().getName());
        Assert.assertNull(familyTree.getMember("Alice").getFather());
        Assert.assertNotNull(familyTree.getMember("Alice"));
        Assert.assertNull(familyTree.getMember("Malfoy").getChildren());
    }

    /**
     * Test add child operation and relations
     */
    @Test
    public void testAddChildOperations() {
        Assert.assertEquals(CommonConstants.CHILD_ADDED, familyTree.addChild("Rose", "Mark", Gender.MALE));
        Assert.assertEquals("Draco Aster", familyTree.getRelationship("Mark", Relationship.SIBLINGS));
        Assert.assertEquals(CommonConstants.PERSON_NOT_FOUND, familyTree.addChild("Jimmy", "Jimmy Jr.", Gender.MALE));
        Assert.assertEquals(CommonConstants.CHILD_ADDITION_FAILED, familyTree.addChild("Bill", "Bill Jr", Gender.MALE));
        Assert.assertEquals(CommonConstants.PERSON_NOT_FOUND, familyTree.addChild("Matt", "Drake", Gender.MALE));
    }

    /**
     * Test existing family relationships
     */
    @Test
    public void testGetRelationshipsInExistingFamily() {
        Assert.assertEquals("Bill Charlie Ronald Ginerva", familyTree.getRelationship("Percy", Relationship.SIBLINGS));
        Assert.assertEquals("Dominique", familyTree.getRelationship("Remus", Relationship.MATERNAL_AUNT));
        Assert.assertEquals("Louis", familyTree.getRelationship("Remus", Relationship.MATERNAL_UNCLE));
        Assert.assertEquals("NONE", familyTree.getRelationship("Remus", Relationship.PATERNAL_AUNT));
        Assert.assertEquals("NONE", familyTree.getRelationship("Malfoy", Relationship.SISTER_IN_LAW));
        Assert.assertEquals("Hugo", familyTree.getRelationship("Malfoy", Relationship.BROTHER_IN_LAW));
    }

    /**
     * Test relationships after adding new members to the family
     */
    @Test
    public void testNewRelationships() {
        //Setup extended family
        familyTree.addChild("Rose", "Mark", Gender.MALE);
        familyTree.addSpouse("Louis", "Jenny", Gender.FEMALE);
        familyTree.addSpouse("Charlie", "Imogen", Gender.FEMALE);
        familyTree.addChild("Jenny", "Timothy", Gender.MALE);
        familyTree.addChild("Jenny", "Simone", Gender.FEMALE);

        Assert.assertEquals("Simone", familyTree.getRelationship("Timothy", Relationship.SIBLINGS));
        Assert.assertEquals("Bill Percy Ronald Harry",
                familyTree.getRelationship("Imogen", Relationship.BROTHER_IN_LAW));
        Assert.assertEquals("Flora Audrey Helen Imogen",
                familyTree.getRelationship("Ginerva", Relationship.SISTER_IN_LAW));
        Assert.assertEquals("Victoire Dominique", familyTree.getRelationship("Simone", Relationship.PATERNAL_AUNT));
    }

    /**
     * Test processing input file
     */
    @Test
    public void testInputFile() {
        try {
            List<String> resultList = CommonUtil.processInput("src/test/resources/sample_input1.txt", familyTree);
            Assert.assertNotNull(resultList);
            Assert.assertEquals(8, resultList.size());
            Assert.assertEquals(CommonConstants.CHILD_ADDED, resultList.get(0));
            Assert.assertEquals(CommonConstants.PERSON_NOT_FOUND, resultList.get(3));
            Assert.assertEquals(CommonConstants.PERSON_NOT_FOUND, resultList.get(4));
            Assert.assertEquals(CommonConstants.NONE, resultList.get(6));
            Assert.assertEquals("Darcy Alice", resultList.get(7));
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test output when an invalid file path given to process
     */
    @Test (expected = IOException.class)
    public void testInputWithInvalidPath() throws IOException {
        List<String> resultList = CommonUtil.processInput("src/main/resources/sample_inputx.txt", familyTree);
        Assert.assertNotNull(resultList);
    }
}
