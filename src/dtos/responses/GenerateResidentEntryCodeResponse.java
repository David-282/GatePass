package dtos.responses;

import data.models.Type;

public class GenerateResidentEntryCodeResponse {

    private  String code;
    private String residentName;
    private Type codeType;
    private String validTill;
    private String destination;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public Type getCodeType() {
        return codeType;
    }

    public void setCodeType(Type codeType) {
        this.codeType = codeType;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }
}
