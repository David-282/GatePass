package data.repositories;

import data.models.Resident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.RandomCodeGenerator;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ResidentsTest {

    Residents residents;
    Resident firstResident;
    Resident secondResident;

    @BeforeEach
    void startWithThis() {
        residents = new Residents();

        firstResident = new Resident();
        firstResident.setName("Ade Johnson");
        firstResident.setEmail("ade@gmail.com");
        firstResident.setPhoneNumber("08011111111");
        firstResident.setHouseAddress("12 Banana Street");
        firstResident.setDateRegistered(LocalDateTime.now());
        firstResident.setEnabled(true);

        secondResident = new Resident();
        secondResident.setName("Tunde Bello");
        secondResident.setEmail("tunde@gmail.com");
        secondResident.setPhoneNumber("08022222222");
        secondResident.setHouseAddress("5 Mango Close");
        secondResident.setDateRegistered(LocalDateTime.now());
        secondResident.setEnabled(true);
    }

    @Test
    void testThatSizeIsZeroUponCreation() {
        assertEquals(0, residents.count());
    }

    @Test
    void testThatResidentIsSavedAndCountIncreases() {
        residents.save(firstResident);
        residents.save(secondResident);
        assertEquals(2, residents.count());
    }

    @Test
    void testThatSavingTheSameResidentTwiceDoesNotDuplicate() {
        residents.save(firstResident);
        residents.save(firstResident);
        assertEquals(1, residents.count());
    }

    @Test
    void testThatResidentIdIsGeneratedOnSave() {
        residents.save(firstResident);
        assertNotNull(firstResident.getId());
        assertFalse(firstResident.getId().isEmpty());
    }

    @Test
    void testFindByIdReturnsCorrectResident() {
        residents.save(firstResident);
        Resident found = residents.findById(firstResident.getId());
        assertNotNull(found);
        assertEquals(firstResident.getId(), found.getId());
    }

    @Test
    void testFindByIdReturnsNullWhenNotFound() {
        assertNull(residents.findById("nonExistentId"));
    }

    @Test
    void testFindByPhoneNumberReturnsCorrectResident() {
        residents.save(firstResident);
        Resident found = residents.findByPhoneNumber("08011111111");
        assertNotNull(found);
        assertEquals("Ade Johnson", found.getName());
    }

    @Test
    void testFindByPhoneNumberReturnsNullWhenNotFound() {
        assertNull(residents.findByPhoneNumber("00000000000"));
    }

    @Test
    void testFindAllReturnsACopyNotTheOriginalList() {
        residents.save(firstResident);
        residents.findAll().clear();
        assertEquals(1, residents.count());
    }

    @Test
    void testThatResidentIsEnabledByDefault() {
        residents.save(firstResident);
        assertTrue(firstResident.isEnabled());
    }

    @Test
    void testDeleteResident() {
        residents.save(firstResident);
        residents.save(secondResident);
        residents.delete(firstResident);
        assertEquals(1, residents.count());
        assertNull(residents.findById(firstResident.getId()));
    }

    @Test
    void testDeleteById() {
        residents.save(firstResident);
        String id = firstResident.getId();
        residents.deleteById(id);
        assertEquals(0, residents.count());
        assertNull(residents.findById(id));
    }

    @Test
    void testDeleteByIdWithNonExistentIdDoesNothing() {
        residents.save(firstResident);
        residents.deleteById("nonExistentId");
        assertEquals(1, residents.count());
    }

    @Test
    void testDeleteAll() {
        residents.save(firstResident);
        residents.save(secondResident);
        residents.deleteAll();
        assertEquals(0, residents.count());
        assertTrue(residents.findAll().isEmpty());
    }
}