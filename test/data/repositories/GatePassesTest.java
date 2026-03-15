package data.repositories;

import data.models.GatePass;
import data.models.Visitor;
import data.models.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.RandomCodeGenerator;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GatePassesTest {

    GatePasses gatePasses;
    GatePass firstGatePass;
    GatePass secondGatePass;
    Visitor firstVisitor;
    Visitor secondVisitor;

    @BeforeEach
    void startWithThis() {
        gatePasses = new GatePasses();

        firstVisitor = new Visitor();
        firstVisitor.setPhoneNumber("08071151567");
        firstVisitor.setName("Bolaji");
        firstVisitor.setPurposeOfComing("For August Visit");
        firstVisitor.setId(RandomCodeGenerator.codeGenerator());

        secondVisitor = new Visitor();
        secondVisitor.setPhoneNumber("08071151567");
        secondVisitor.setName("Tunde");
        secondVisitor.setPurposeOfComing("For personal reasons");
        secondVisitor.setId(RandomCodeGenerator.codeGenerator());

        firstGatePass = new GatePass();
        firstGatePass.setResidentId(RandomCodeGenerator.codeGenerator());
        firstGatePass.setExpirationDate(LocalDateTime.now().plusDays(1));
        firstGatePass.setVisitor(firstVisitor);
        firstGatePass.setPassType(Type.ENTRY);
        firstGatePass.setCode(RandomCodeGenerator.codeGenerator());

        secondGatePass = new GatePass();
        secondGatePass.setResidentId(RandomCodeGenerator.codeGenerator());
        secondGatePass.setExpirationDate(LocalDateTime.now().plusDays(2));
        secondGatePass.setVisitor(secondVisitor);
        secondGatePass.setPassType(Type.EXIT);
        secondGatePass.setCode(RandomCodeGenerator.codeGenerator());
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
        assertEquals(firstGatePass.getId(), found.getId());
    }

    @Test
    void testFindByIdReturnsNullWhenNotFound() {
        assertNull(gatePasses.findById("nonExistentId"));
    }

    @Test
    void testFindAllReturnsACopyNotTheOriginalList() {
        gatePasses.save(firstGatePass);
        gatePasses.findAll().clear();
        assertEquals(1, gatePasses.count());
    }

    @Test
    void testGatePassHasVisitorAttached() {
        gatePasses.save(firstGatePass);
        GatePass found = gatePasses.findById(firstGatePass.getId());
        assertNotNull(found.getVisitor());
        assertEquals("Bolaji", found.getVisitor().getName());
    }

    @Test
    void testGatePassHasCorrectPassType() {
        gatePasses.save(firstGatePass);
        GatePass found = gatePasses.findById(firstGatePass.getId());
        assertEquals(Type.ENTRY, found.getPassType());
    }

    @Test
    void testGatePassCodeIsNotNull() {
        gatePasses.save(firstGatePass);
        GatePass found = gatePasses.findById(firstGatePass.getId());
        assertNotNull(found.getCode());
    }

    @Test
    void testGatePassExpirationDateIsInTheFuture() {
        gatePasses.save(firstGatePass);
        GatePass found = gatePasses.findById(firstGatePass.getId());
        assertTrue(found.getExpirationDate().isAfter(LocalDateTime.now()));
    }

    @Test
    void testDeleteById() {
        gatePasses.save(firstGatePass);
        String id = firstGatePass.getId();
        gatePasses.deleteById(id);
        assertEquals(0, gatePasses.count());
        assertNull(gatePasses.findById(id));
    }

    @Test
    void testDeleteByIdWithNonExistentIdDoesNothing() {
        gatePasses.save(firstGatePass);
        gatePasses.deleteById("nonExistentId");
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
    void testDeleteAll() {
        gatePasses.save(firstGatePass);
        gatePasses.save(secondGatePass);
        gatePasses.deleteAll();
        assertEquals(0, gatePasses.count());
        assertTrue(gatePasses.findAll().isEmpty());
    }
}