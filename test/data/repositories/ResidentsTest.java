package data.repositories;

import data.models.Resident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResidentsTest {

    Residents residents;
    Resident firstResident;
    Resident secondResident;

    @BeforeEach
    void startWithThis() {
        residents = new Residents();

        firstResident = new Resident();
        firstResident.setName("John Resident");
        firstResident.setPhoneNumber("08011112222");
        firstResident.setHouseAddress("12 Lagos Street");

        secondResident = new Resident();
        secondResident.setName("Jane Resident");
        secondResident.setPhoneNumber("08033334444");
        secondResident.setHouseAddress("45 Abuja Avenue");
    }

    @Test
    void testThatSizeIsZeroUponCreation() {
        assertEquals(0, residents.count());
    }

    @Test
    void testThatResidentIsSavedAndCountIncreases() {
        assertEquals(0, residents.count());

        residents.save(firstResident);
        residents.save(secondResident);

        assertEquals(2, residents.count());
    }

    @Test
    void testThatSavedResidentIsAssignedAnId() {
        residents.save(firstResident);
        assertEquals(1, firstResident.getId());
    }

    @Test
    void testThatIdsAutoIncrementWorksForEachResident() {
        residents.save(firstResident);
        residents.save(secondResident);

        assertNotEquals(firstResident.getId(), secondResident.getId());
    }

    @Test
    void testThatSavingTheSameResidentTwiceDoesNotDuplicate() {
        residents.save(firstResident);
        residents.save(firstResident);

        assertEquals(1, residents.count());
    }

    @Test
    void testFindAllReturnsACopyNotTheOriginalList() {
        residents.save(firstResident);
        residents.save(secondResident);
        residents.findAll().clear();

        assertEquals(2, residents.count());
    }

    @Test
    void testFindByIdReturnsTheActualObject() {
        residents.save(firstResident);
        int id = firstResident.getId();

        Resident found = residents.findById(id);

        assertNotNull(found);
        assertEquals("John Resident", found.getName());
    }

    @Test
    void testFindByIdReturnsNullWhenObjectNotFound() {
        Resident found = residents.findById(999);
        assertNull(found);
    }

    @Test
    void testDelete() {
        residents.save(firstResident);
        assertEquals(1, residents.count());

        residents.delete(firstResident);

        assertEquals(0, residents.count());
    }

    @Test
    void testDeleteRemovesCorrectResident() {
        residents.save(firstResident);
        residents.save(secondResident);

        residents.delete(firstResident);

        assertEquals(1, residents.count());
        assertNull(residents.findById(firstResident.getId()));
    }

    @Test
    void testDeleteByIdWorksWell() {
        residents.save(firstResident);
        int id = firstResident.getId();

        residents.deleteById(id);

        assertEquals(0, residents.count());
        assertNull(residents.findById(id));
    }

    @Test
    void testDeleteByIdWithNonExistentIdDoesNothing() {
        residents.save(firstResident);

        residents.deleteById(999);

        assertEquals(1, residents.count());
    }

    @Test
    void testDeleteByObject() {
        residents.save(firstResident);
        residents.save(secondResident);

        residents.deleteByObject(secondResident);

        assertEquals(1, residents.count());
        assertNull(residents.findById(secondResident.getId()));
    }

    @Test
    void testDeleteAll() {
        residents.save(firstResident);
        residents.save(secondResident);
        assertEquals(2, residents.count());

        residents.deleteAll();

        assertEquals(0, residents.count());
    }

    @Test
    void testCountAfterMultipleOperations() {
        residents.save(firstResident);
        residents.save(secondResident);
        assertEquals(2, residents.count());

        residents.delete(firstResident);
        assertEquals(1, residents.count());

        residents.deleteAll();
        assertEquals(0, residents.count());
    }
}