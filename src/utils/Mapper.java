package utils;

import data.models.GatePass;
import data.models.Resident;
import data.models.Visitor;
import dtos.requests.*;
import dtos.responses.*;
import data.models.Type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mapper {

    public static Resident map(OnboardResidentRequest onboardResidentRequest){
        Resident resident = new Resident();
        resident.setName(onboardResidentRequest.getName());

        resident.setDateRegistered(LocalDateTime.now());
        resident.setPhoneNumber(onboardResidentRequest.getPhoneNumber());
        resident.setEmail(onboardResidentRequest.getEmail());
        resident.setHouseAddress(onboardResidentRequest.getAddress());

        return resident;
    }

    public static OnboardResidentResponse map (Resident resident){

        OnboardResidentResponse response = new OnboardResidentResponse();

        response.setResidentName(resident.getName());
        response.setResidentId(resident.getId());
        response.setDateRegistered(resident.getDateRegistered());

        return response;

    }

    public static Visitor map(GenerateVisitorEntryCodeRequest request){
        Visitor visitor = new Visitor();
        visitor.setId(RandomCodeGenerator.codeGenerator());
        visitor.setName(request.getVisitorName());
        visitor.setPurposeOfComing(request.getPurposeOfVisit());
        visitor.setPhoneNumber(request.getPhoneNumber());

        return visitor;
    }

    public static GatePass map(GenerateVisitorEntryCodeRequest request, Visitor visitor){
        GatePass pass = new GatePass();
        pass.setResidentId(request.getResidentId());
        pass.setVisitor(visitor);
        pass.setPassType(Type.ENTRY);

        pass.setCreatedAt(LocalDateTime.now());
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(request.getValidHour());
        LocalDateTime midnight = LocalDateTime.now().toLocalDate().atTime(23,59,59);
        LocalDateTime timeToUse;

        if (expirationTime.isAfter(midnight)){
        timeToUse = midnight;}
            else{
            timeToUse = expirationTime;
        }
        pass.setExpirationDate(timeToUse);
        return pass;
    }

    public static GenerateVisitorEntryCodeResponse map ( GatePass gatePass){

        GenerateVisitorEntryCodeResponse response = new GenerateVisitorEntryCodeResponse();
        response.setCodeType(gatePass.getPassType());
        response.setCode(gatePass.getCode());
        response.setVisitorName(gatePass.getVisitor().getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        response.setValidTill(gatePass.getExpirationDate().format(formatter));
        return response;
    }

    public static ValidateCodeResponse map(ValidateCodeRequest request, GatePass pass, Resident resident){

        ValidateCodeResponse response = new ValidateCodeResponse();
        response.setCodeType(request.getCodeType());
        response.setValid(true);
        response.setVisitorsName(pass.getVisitor().getName());
        response.setResidentName(resident.getName());

        return response;
    }

    public static GatePass map(GenerateResidentEntryCodeRequest request){

        GatePass pass = new GatePass();
        pass.setResidentId(request.getResidentId());
        pass.setPassType(Type.ENTRY);
        pass.setCreatedAt(LocalDateTime.now());

        pass.setExpirationDate(request.getValidTill());

        return pass;
    }

    public static GenerateResidentEntryCodeResponse map (Resident resident, GatePass pass){

        GenerateResidentEntryCodeResponse response = new GenerateResidentEntryCodeResponse();

        response.setDestination(resident.getHouseAddress());
        response.setCodeType(pass.getPassType());
        response.setResidentName(resident.getName());
        response.setCode(pass.getCode());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        response.setValidTill(pass.getExpirationDate().format(formatter));

        return response;
    }

    public static  GatePass map(GatePass pass, GenerateExitCodeRequest request){


        GatePass existCode = new GatePass();

        existCode.setPassType(Type.EXIT);
        existCode.setResidentId(pass.getResidentId());
        existCode.setCreatedAt(LocalDateTime.now());
        existCode.setExpirationDate(request.getValidTill());

         return existCode;
    }

    public static GenerateExitCodeResponse map(GatePass existCode,Resident resident,GatePass pass){
        GenerateExitCodeResponse response = new GenerateExitCodeResponse();
        response.setCodeType(existCode.getPassType());
        response.setHouseAddress(resident.getHouseAddress());
        response.setCode(existCode.getCode());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        response.setValidTill(existCode.getExpirationDate().format(formatter));

        if (pass.getVisitor() == null){
            response.setName(resident.getName());
        }
        else{
            response.setHouseAddress(pass.getVisitor().getName());
        }
        return response;
    }

    public static ExtendCodeResponse extendCodeMap(GatePass pass) {
        ExtendCodeResponse response = new ExtendCodeResponse();
        response.setCode(pass.getCode());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        response.setNewValidTill(pass.getExpirationDate().format(formatter));
        return response;
    }
}
