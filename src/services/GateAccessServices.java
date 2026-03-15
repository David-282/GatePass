package services;

import data.models.GatePass;
import data.models.Resident;
import data.models.Visitor;
import data.repositories.GatePassRepository;
import data.repositories.GatePasses;
import data.repositories.ResidentRepository;
import data.repositories.Residents;
import dtos.requests.*;
import dtos.responses.*;
import exceptions.InvalidGatePassException;
import exceptions.ResidentDisabledException;
import exceptions.ResidentDoesNotExistException;
import utils.RandomCodeGenerator;

import java.time.LocalDateTime;
import java.util.List;

import static utils.Mapper.extendCodeMap;
import static utils.Mapper.map;

public class GateAccessServices {

    private ResidentRepository residentRepository = new Residents();
    private GatePassRepository gatePassRepository = new GatePasses();

    private String generateCode() {
        return RandomCodeGenerator.codeGenerator();
    }


    public GenerateVisitorEntryCodeResponse generateVisitorsEntryCode(GenerateVisitorEntryCodeRequest request) {

        Resident exsitingResident = residentRepository.findById(request.getResidentId());

        if (exsitingResident == null) {
            throw new ResidentDoesNotExistException("Resident Does Not Exist In Our Server.");
        }

        validateResidentIsActive(exsitingResident);
        Visitor visitor = map(request);
//        visitor.setId(RandomCodeGenerator.codeGenerator());
//        visitor.setName(request.getVisitorName());
//        visitor.setPurposeOfComing(request.getPurposeOfVisit());
//        visitor.setPhoneNumber(request.getPhoneNumber());


        GatePass pass = map(request, visitor);
//        pass.setResidentId(request.getResidentId());
//        pass.setVisitor(visitor);
//        pass.setPassType(Type.ENTRY);
        pass.setCode(generateCode());
//        pass.setCreatedAt(LocalDateTime.now());
//        pass.setExpirationDate(LocalDateTime.now().plusHours(request.getValidHour()));
        gatePassRepository.save(pass);

//            GenerateVisitorEntryCodeResponse response = new GenerateVisitorEntryCodeResponse();
//            response.setCodeType(pass.getPassType());
//            response.setCode(pass.getCode());
//            response.setVisitorName(pass.getVisitor().getName());
//            response.setResidentName(resident.getName());

        return map(pass);
    }


    public String disableCode(String code) {

        GatePass pass = gatePassRepository.findByCode(code);

        validateCodeExistence(pass);

        validatingIfCodeIsActive(pass);

        Resident resident = residentRepository.findById(pass.getResidentId());

        validateResidentIsActive(resident);

        pass.setValid(false);

        return (code) + " Has been Disabled";
    }


    public ValidateCodeResponse validateCode(ValidateCodeRequest request) {

        GatePass pass = gatePassRepository.findByCode(request.getCode());
        validateCodeExistence(pass);
        Resident resident = residentRepository.findById(pass.getResidentId());
        validatingIfCodeIsActive(pass);

        if (!pass.isValid()) {
            throw new InvalidGatePassException("Gatepass is not active ");
        }

//        ValidateCodeResponse response = new ValidateCodeResponse();
//        response.setCodeType(request.getCodeType());
//        response.setValid(true);
//        response.setVisitorsName(pass.getVisitor().getName());
//        response.setResidentName(resident.getName());

        return map(request, pass, resident);

    }

    public GenerateResidentEntryCodeResponse generateResidentEntryCode(GenerateResidentEntryCodeRequest request) {

        Resident resident = residentRepository.findById(request.getResidentId());

        if (resident == null) {
            throw new ResidentDoesNotExistException("Resident does not exist.");
        }


        validateResidentIsActive(resident);


        GatePass pass = map(request);
        pass.setCode(generateCode());


//        pass.setResidentId(request.getResidentId());
//        pass.setPassType(Type.ENTRY);
//
//        pass.setCreatedAt(LocalDateTime.now());
//        pass.setValid(true);
//        pass.setExpirationDate(request.getValidTill());

//        GenerateResidentEntryCodeResponse response = new GenerateResidentEntryCodeResponse();
//
//        response.setDestination(resident.getHouseAddress());
//        response.setCodeType(pass.getPassType());
//        response.setResidentName(resident.getName());
//        response.setCode(pass.getCode());
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        response.setValidTill(pass.getExpirationDate().format(formatter));

        return map(resident, pass);
    }


    public GenerateExitCodeResponse generateExitCode(GenerateExitCodeRequest request) {

        GatePass pass = gatePassRepository.findByCode(request.getCode());

        validateCodeExistence(pass);

        Resident resident = residentRepository.findById(pass.getResidentId());

        if (!pass.isValid()) {
            throw new InvalidGatePassException("Code Has been disabled.");
        }

        validateResidentIsActive(resident);

        pass.setValid(false);

        GatePass existCode = map(pass, request);
        existCode.setCode(generateCode());

        gatePassRepository.save(existCode);

//        GatePass existCode = new GatePass();
//
//        existCode.setPassType(Type.EXIT);
//        existCode.setResidentId(pass.getResidentId());
//        existCode.setCode(RandomCode);
//        existCode.setCreatedAt(LocalDateTime.now());
//        existCode.setExpirationDate(request.getValidTill());

//        GenerateExitCodeResponse response = new GenerateExitCodeResponse();
//        response.setCodeType(existCode.getPassType());
//        response.setHouseAddress(resident.getHouseAddress());
//        response.setCode(existCode.getCode());
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        response.setValidTill(existCode.getExpirationDate().format(formatter));
//
//        if (pass.getVisitor() == null){
//            response.setName(resident.getName());
//        }
//        else{
//            response.setHouseAddress(pass.getVisitor().getName());
//        }

        return map(existCode, resident, pass);
    }


    public ExtendCodeResponse extendCode(ExtendCodeRequest request) {
        GatePass pass = gatePassRepository.findByCode(request.getCode());

        validateCodeExistence(pass);
        validatingIfCodeIsActive(pass);

        if (!pass.isValid()) {
            throw new InvalidGatePassException("Code is not active.");
        }
        LocalDateTime newExpiration = pass.getExpirationDate().plusHours(request.getHoursToExtendBy());
        LocalDateTime midnight = LocalDateTime.now().toLocalDate().atTime(23, 59, 59);
        if (newExpiration.isAfter(midnight)) {
            newExpiration = midnight;
        }
        pass.setExpirationDate(newExpiration);
        return extendCodeMap(pass);
    }


    public List<GatePass> viewAllGatePasses() {
        return gatePassRepository.findAll();
    }
    private static void validatingIfCodeIsActive(GatePass pass) {
        if (LocalDateTime.now().isAfter(pass.getExpirationDate())) {
            throw new InvalidGatePassException("Gate pass is no longer active, Its is expired.");
        }
    }

    private static void validateCodeExistence(GatePass pass) {
        if (pass == null) {
            throw new InvalidGatePassException("Code is not in Existence");
        }
    }

    private static void validateResidentIsActive(Resident resident) {
        if (!resident.isEnabled()) {
            throw new ResidentDisabledException("Resident is Disabled");
        }
    }
}