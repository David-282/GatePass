package data.repositories;

import data.models.GatePass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GatePassesTest {

    GatePasses gatePasses;
    GatePass firstGatePass;
    GatePass secondGatePass;

    @BeforeEach
    void startWithThis() {
        gatePasses = new GatePasses();

        firstGatePass = new GatePass();
        firstGatePass.setResidentId(1);
        firstGatePass.setVisitorsId(101);
        firstGatePass.setExpirationDate(LocalDateTime.now().plusDays(1));

        secondGatePass = new GatePass();
        secondGatePass.setResidentId(2);
        secondGatePass.setVisitorsId(102);
        secondGatePass.setExpirationDate(LocalDateTime.now().plusDays(2));
    }

    @Test
    void testThatSizeIsZeroUponCreation() {
        assertEquals(0, gatePasses.count());
    }

    @Test
    void testThatGatePassIsSavedAndCountIncreases() {
        gatePasses.save(firstGatePass);
        gatePasses.save(secondGatePass);
        assertEquals(2, gatePasses.count());
    }

    @Test
    void testThatSavedGatePassIsAssignedAnId() {
        gatePasses.save(firstGatePass);
        assertEquals(1, firstGatePass.getId());
    }


    @Test
    void testThatSavingTheSameGatePassTwiceDoesNotDuplicate() {
        gatePasses.save(firstGatePass);
        gatePasses.save(firstGatePass);
        assertEquals(1, gatePasses.count());
    }

    @Test
    void testThatGatePassIsValidByDefault() {
        gatePasses.save(firstGatePass);
        assertTrue(firstGatePass.isValid());
    }



    @Test
    void testFindByIdReturnsCorrectGatePass() {
        gatePasses.save(firstGatePass);
        GatePass found = gatePasses.findById(firstGatePass.getId());
        assertNotNull(found);
        assertEquals(firstGatePass.getVisitorsId(), found.getVisitorsId());
    }

    @Test
    void testFindByIdReturnsNullWhenNotFound() {
        assertNull(gatePasses.findById(999));
    }

    @Test
    void testFindAllReturnsACopyNotTheOriginalList() {
        gatePasses.save(firstGatePass);
        gatePasses.findAll().clear();
        assertEquals(1, gatePasses.count());
    }

    @Test
    void testDelete() {
        gatePasses.save(firstGatePass);
        gatePasses.save(secondGatePass);
        gatePasses.delete(firstGatePass);
        assertEquals(1, gatePasses.count());
        assertNull(gatePasses.findById(firstGatePass.getId()));
    }

    @Test
    void testDeleteById() {
        gatePasses.save(firstGatePass);
        int id = firstGatePass.getId();
        gatePasses.deleteById(id);
        assertEquals(0, gatePasses.count());
        assertNull(gatePasses.findById(id));
    }

    @Test
    void testDeleteByIdWithNonExistentIdDoesNothing() {
        gatePasses.save(firstGatePass);
        gatePasses.deleteById(999);
        assertEquals(1, gatePasses.count());
    }

    @Test
    void testDeleteByObject() {
        gatePasses.save(firstGatePass);
        gatePasses.save(secondGatePass);
        gatePasses.deleteByObject(secondGatePass);
        assertEquals(1, gatePasses.count());
        assertNull(gatePasses.findById(secondGatePass.getId()));
    }

    @Test
    void testDeleteAll() {
        gatePasses.save(firstGatePass);
        gatePasses.save(secondGatePass);
        gatePasses.deleteAll();
        assertEquals(0, gatePasses.count());
        assertTrue(gatePasses.findAll().isEmpty());
    }
}