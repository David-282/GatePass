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
    void testThatSavedVisitorIsAssignedAnId() {
        visitors.save(firstVisitor);
        assertEquals(firstVisitor.getId(), 1);
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
    void testFindByIdReturnTheActualObject() {
        visitors.save(firstVisitor);
        int id = firstVisitor.getId();

        Visitor found = visitors.findById(id);

        assertNotNull(found);
        assertEquals("August Visitor", found.getName());
    }

    @Test
    void testFindByIdReturnsNullWhenObjectNotFound() {
        Visitor found = visitors.findById(999);
        assertNull(found);
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
    void testDeleteByIdWorksWell() {
        visitors.save(firstVisitor);
        int id = firstVisitor.getId();

        visitors.deleteById(id);

        assertEquals(0, visitors.count());
        assertNull(visitors.findById(id));
    }


    @Test
    void testDeleteByObject() {
        visitors.save(firstVisitor);
        visitors.save(secondVisitor);

        visitors.deleteByObject(secondVisitor);

        assertEquals(1, visitors.count());
        assertNull(visitors.findById(secondVisitor.getId()));
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