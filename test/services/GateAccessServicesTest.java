package services;

import data.models.GatePass;
import dtos.requests.*;
import dtos.responses.*;
import exceptions.InvalidGatePassException;
import exceptions.ResidentDisabledException;
import exceptions.ResidentDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GatePassServicesTest {

    GateAccessServices gatePassServices;
    ResidentManagementService residentService;
    String residentId;
    String secondResidentId;

    @BeforeEach
    void startWithThis() {
        gatePassServices = new GateAccessServices();
        residentService = new ResidentManagementService();

        OnboardResidentRequest firstRequest = new OnboardResidentRequest();
        firstRequest.setName("Ade Johnson");
        firstRequest.setEmail("ade@gmail.com");
        firstRequest.setPhoneNumber("08011111111");
        firstRequest.setAddress("12 Banana Street");

        OnboardResidentRequest secondRequest = new OnboardResidentRequest();
        secondRequest.setName("Tunde Bello");
        secondRequest.setEmail("tunde@gmail.com");
        secondRequest.setPhoneNumber("08022222222");
        secondRequest.setAddress("5 Mango Close");

        OnboardResidentResponse firstResponse = residentService.onboardingResident(firstRequest);
        OnboardResidentResponse secondResponse = residentService.onboardingResident(secondRequest);

        residentId = firstResponse.getResidentId();
        secondResidentId = secondResponse.getResidentId();
    }

    private GenerateVisitorEntryCodeRequest buildVisitorRequest(String residentId) {
        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setResidentId(residentId);
        request.setVisitorName("Bolaji");
        request.setPhoneNumber("08055555555");
        request.setPurposeOfVisit("Family visit");
        request.setValidHour(2);
        return request;
    }

    private GenerateResidentEntryCodeRequest buildResidentEntryRequest(String residentId) {
        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(residentId);
        request.setValidTill(LocalDateTime.now().plusHours(3));
        return request;
    }

    @Test
    void testGenerateVisitorEntryCodeSuccessfully() {
        GenerateVisitorEntryCodeResponse response = gatePassServices.generateVisitorsEntryCode(buildVisitorRequest(residentId));
        assertNotNull(response);
        assertNotNull(response.getCode());
        assertNotNull(response.getValidTill());
        assertEquals("Bolaji", response.getVisitorName());
    }

    @Test
    void testGenerateVisitorEntryCodeForNonExistentResidentThrowsException() {
        assertThrows(ResidentDoesNotExistException.class, () ->
                gatePassServices.generateVisitorsEntryCode(buildVisitorRequest("nonExistentId")));
    }

    @Test
    void testGenerateVisitorEntryCodeForDisabledResidentThrowsException() {
        residentService.disableResident("08011111111");
        assertThrows(ResidentDisabledException.class, () ->
                gatePassServices.generateVisitorsEntryCode(buildVisitorRequest(residentId)));
    }

    @Test
    void testGenerateResidentEntryCodeSuccessfully() {
        GenerateResidentEntryCodeResponse response = gatePassServices.generateResidentEntryCode(buildResidentEntryRequest(residentId));
        assertNotNull(response);
        assertNotNull(response.getCode());
        assertEquals("Ade Johnson", response.getResidentName());
    }

    @Test
    void testGenerateResidentEntryCodeForNonExistentResidentThrowsException() {
        assertThrows(ResidentDoesNotExistException.class, () ->
                gatePassServices.generateResidentEntryCode(buildResidentEntryRequest("nonExistentId")));
    }

    @Test
    void testGenerateResidentEntryCodeForDisabledResidentThrowsException() {
        residentService.disableResident("08011111111");
        assertThrows(ResidentDisabledException.class, () ->
                gatePassServices.generateResidentEntryCode(buildResidentEntryRequest(residentId)));
    }

    @Test
    void testValidateCodeSuccessfully() {
        GenerateVisitorEntryCodeResponse generated = gatePassServices.generateVisitorsEntryCode(buildVisitorRequest(residentId));
        ValidateCodeRequest request = new ValidateCodeRequest();
        request.setCode(generated.getCode());
        ValidateCodeResponse response = gatePassServices.validateCode(request);
        assertNotNull(response);
        assertTrue(response.isValid());
        assertEquals("Bolaji", response.getVisitorsName());
    }

    @Test
    void testValidateNonExistentCodeThrowsException() {
        ValidateCodeRequest request = new ValidateCodeRequest();
        request.setCode("nonExistentCode");
        assertThrows(InvalidGatePassException.class, () ->
                gatePassServices.validateCode(request));
    }

    @Test
    void testValidateDisabledCodeThrowsException() {
        GenerateVisitorEntryCodeResponse generated = gatePassServices.generateVisitorsEntryCode(buildVisitorRequest(residentId));
        gatePassServices.disableCode(generated.getCode());
        ValidateCodeRequest request = new ValidateCodeRequest();
        request.setCode(generated.getCode());
        assertThrows(InvalidGatePassException.class, () ->
                gatePassServices.validateCode(request));
    }

    @Test
    void testGenerateExitCodeSuccessfully() {
        GenerateVisitorEntryCodeResponse entryResponse = gatePassServices.generateVisitorsEntryCode(buildVisitorRequest(residentId));
        GenerateExitCodeRequest exitRequest = new GenerateExitCodeRequest();
        exitRequest.setCode(entryResponse.getCode());
        exitRequest.setValidTill(LocalDateTime.now().plusHours(1));
        GenerateExitCodeResponse exitResponse = gatePassServices.generateExitCode(exitRequest);
        assertNotNull(exitResponse);
        assertNotNull(exitResponse.getCode());
    }

    @Test
    void testGenerateExitCodeWithInvalidCodeThrowsException() {
        GenerateExitCodeRequest exitRequest = new GenerateExitCodeRequest();
        exitRequest.setCode("nonExistentCode");
        exitRequest.setValidTill(LocalDateTime.now().plusHours(1));
        assertThrows(InvalidGatePassException.class, () ->
                gatePassServices.generateExitCode(exitRequest));
    }

    @Test
    void testExtendCodeSuccessfully() {
        GenerateVisitorEntryCodeResponse generated = gatePassServices.generateVisitorsEntryCode(buildVisitorRequest(residentId));
        ExtendCodeRequest extendRequest = new ExtendCodeRequest();
        extendRequest.setCode(generated.getCode());
        extendRequest.setHoursToExtendBy(1);
        ExtendCodeResponse response = gatePassServices.extendCode(extendRequest);
        assertNotNull(response);
        assertNotNull(response.getNewValidTill());
    }

    @Test
    void testExtendNonExistentCodeThrowsException() {
        ExtendCodeRequest extendRequest = new ExtendCodeRequest();
        extendRequest.setCode("nonExistentCode");
        extendRequest.setHoursToExtendBy(1);
        assertThrows(InvalidGatePassException.class, () ->
                gatePassServices.extendCode(extendRequest));
    }

    @Test
    void testDisableCodeSuccessfully() {
        GenerateVisitorEntryCodeResponse generated = gatePassServices.generateVisitorsEntryCode(buildVisitorRequest(residentId));
        String response = gatePassServices.disableCode(generated.getCode());
        assertNotNull(response);
        assertTrue(response.contains("Disabled"));
    }

    @Test
    void testDisableNonExistentCodeThrowsException() {
        assertThrows(InvalidGatePassException.class, () ->
                gatePassServices.disableCode("nonExistentCode"));
    }

    @Test
    void testViewAllGatePassesReturnsCorrectList() {
        gatePassServices.generateVisitorsEntryCode(buildVisitorRequest(residentId));
        gatePassServices.generateVisitorsEntryCode(buildVisitorRequest(secondResidentId));
        List<GatePass> passes = gatePassServices.viewAllGatePasses();
        assertEquals(2, passes.size());
    }

    @Test
    void testViewAllGatePassesReturnsEmptyListInitially() {
        List<GatePass> passes = gatePassServices.viewAllGatePasses();
        assertTrue(passes.isEmpty());
    }
}