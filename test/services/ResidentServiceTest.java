package services;

import dtos.requests.OnboardResidentRequest;
import dtos.responses.OnboardResidentResponse;
import exceptions.ResidentAlreadyRegisteredException;
import exceptions.ResidentDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import data.models.Resident;

import static org.junit.jupiter.api.Assertions.*;

class ResidentServiceTest {

    ResidentManagementService residentService;
    OnboardResidentRequest firstRequest;
    OnboardResidentRequest secondRequest;

    @BeforeEach
    void startWithThis() {
        residentService = new ResidentManagementService();

        firstRequest = new OnboardResidentRequest();
        firstRequest.setName("Ade Johnson");
        firstRequest.setEmail("ade@gmail.com");
        firstRequest.setPhoneNumber("08011111111");
        firstRequest.setAddress("12 Banana Street");

        secondRequest = new OnboardResidentRequest();
        secondRequest.setName("Tunde Bello");
        secondRequest.setEmail("tunde@gmail.com");
        secondRequest.setPhoneNumber("08022222222");
        secondRequest.setAddress("5 Mango Close");
    }

    @Test
    void testOnboardResidentSuccessfully() {
        OnboardResidentResponse response = residentService.onboardingResident(firstRequest);
        assertNotNull(response);
        assertEquals("Ade Johnson", response.getResidentName());
        assertNotNull(response.getResidentId());
        assertNotNull(response.getDateRegistered());
    }

    @Test
    void testOnboardMultipleResidentsSuccessfully() {
        residentService.onboardingResident(firstRequest);
        residentService.onboardingResident(secondRequest);
        List<Resident> residents = residentService.veiwResident();
        assertEquals(2, residents.size());
    }

    @Test
    void testOnboardSameResidentTwiceThrowsException() {
        residentService.onboardingResident(firstRequest);
        assertThrows(ResidentAlreadyRegisteredException.class, () ->
                residentService.onboardingResident(firstRequest));
    }

    @Test
    void testOnboardResidentWithSamePhoneNumberThrowsException() {
        residentService.onboardingResident(firstRequest);
        OnboardResidentRequest duplicate = new OnboardResidentRequest();
        duplicate.setName("Different Name");
        duplicate.setEmail("different@gmail.com");
        duplicate.setPhoneNumber("08011111111");
        duplicate.setAddress("Different Address");
        assertThrows(ResidentAlreadyRegisteredException.class, () ->
                residentService.onboardingResident(duplicate));
    }

    @Test
    void testDisableResidentSuccessfully() {
        residentService.onboardingResident(firstRequest);
        String response = residentService.disableResident("08011111111");
        assertNotNull(response);
        assertTrue(response.contains("Ade Johnson"));
    }

    @Test
    void testDisableResidentThatDoesNotExistThrowsException() {
        assertThrows(ResidentDoesNotExistException.class, () ->
                residentService.disableResident("00000000000"));
    }

    @Test
    void testDisabledResidentIsActuallyDisabled() {
        residentService.onboardingResident(firstRequest);
        residentService.disableResident("08011111111");
        List<Resident> residents = residentService.veiwResident();
        Resident resident = residents.get(0);
        assertFalse(resident.isEnabled());
    }

    @Test
    void testDeleteResidentSuccessfully() {
        residentService.onboardingResident(firstRequest);
        residentService.onboardingResident(secondRequest);
        residentService.deleteResident("08011111111");
        List<Resident> residents = residentService.veiwResident();
        assertEquals(1, residents.size());
    }

    @Test
    void testDeleteResidentThatDoesNotExistThrowsException() {
        assertThrows(ResidentDoesNotExistException.class, () ->
                residentService.deleteResident("00000000000"));
    }

    @Test
    void testViewAllResidentsReturnsEmptyListInitially() {
        List<Resident> residents = residentService.veiwResident();
        assertTrue(residents.isEmpty());
    }

    @Test
    void testViewAllResidentsReturnsCorrectList() {
        residentService.onboardingResident(firstRequest);
        residentService.onboardingResident(secondRequest);
        List<Resident> residents = residentService.veiwResident();
        assertEquals(2, residents.size());
    }

    @Test
    void testResidentIsEnabledAfterOnboarding() {
        residentService.onboardingResident(firstRequest);
        List<Resident> residents = residentService.veiwResident();
        assertTrue(residents.get(0).isEnabled());
    }

    @Test
    void testResidentHasIdAfterOnboarding() {
        residentService.onboardingResident(firstRequest);
        List<Resident> residents = residentService.veiwResident();
        assertNotNull(residents.get(0).getId());
        assertFalse(residents.get(0).getId().isEmpty());
    }

    @Test
    void testResidentHasDateRegisteredAfterOnboarding() {
        residentService.onboardingResident(firstRequest);
        List<Resident> residents = residentService.veiwResident();
        assertNotNull(residents.get(0).getDateRegistered());
    }
}