package data.repositories;

import data.models.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisitorsTest {

    Visitors visitors;
    Visitor firstVisitor;
    Visitor secondVisitor;

    @BeforeEach
    void startWithThis() {
        visitors = new Visitors();

        firstVisitor = new Visitor();
        firstVisitor.setName("August Visitor");
        firstVisitor.setPhoneNumber("08071151567");
        firstVisitor.setPurposeOfComing("For personal reasons");

        secondVisitor = new Visitor();
        secondVisitor.setName("Second Visitor");
        secondVisitor.setPhoneNumber("08099998888");
        secondVisitor.setPurposeOfComing("Official visit");
    }

    @Test
    void testThatSizeIsZeroUponCreation() {
        assertEquals(0, visitors.count());
    }

    @Test
    void testThatVisitorIsSavedAndCountIncreases() {
        assertEquals(0, visitors.count());

        visitors.save(firstVisitor);
        visitors.save(secondVisitor);

        assertEquals(2, visitors.count());
    }


    @Test
    void testThatIdsAutoIncrementWorksForEachVisitor() {
        visitors.save(firstVisitor);
        visitors.save(secondVisitor);

        assertNotEquals(firstVisitor.getId(), secondVisitor.getId());
    }

    @Test
    void testThatSavingTheSameVisitorTwiceDoesNotDuplicate() {
        visitors.save(firstVisitor);
        visitors.save(firstVisitor);

        assertEquals(1, visitors.count());
    }

    @Test
    void testFindAllReturnsACopyNotTheOriginalList() {
        visitors.save(firstVisitor);
        visitors.save(secondVisitor);
        visitors.findAll().clear();

        assertEquals(2, visitors.count());
    }



    @Test
    void testDelete() {
        visitors.save(firstVisitor);
        assertEquals(1, visitors.count());

        visitors.delete(firstVisitor);

        assertEquals(0, visitors.count());
    }

    @Test
    void testDeleteRemovesCorrectVisitor() {
        visitors.save(firstVisitor);
        visitors.save(secondVisitor);

        visitors.delete(firstVisitor);

        assertEquals(1, visitors.count());
        assertNull(visitors.findById(firstVisitor.getId()));
    }





    @Test
    void testDeleteAll() {
        visitors.save(firstVisitor);
        visitors.save(secondVisitor);
        assertEquals(2, visitors.count());

        visitors.deleteAll();

        assertEquals(0, visitors.count());
    }


    @Test
    void testCountAfterMultipleOperations() {
        visitors.save(firstVisitor);
        visitors.save(secondVisitor);
        assertEquals(2, visitors.count());

        visitors.delete(firstVisitor);
        assertEquals(1, visitors.count());

        visitors.deleteAll();
        assertEquals(0, visitors.count());
    }
}