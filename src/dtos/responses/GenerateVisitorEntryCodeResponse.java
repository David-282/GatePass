package dtos.responses;

import data.models.Type;

public class GenerateVisitorEntryCodeResponse {

    private String code;
    private Type codeType;
    private String visitorName;
    private String validTill;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public Type getCodeType() {
        return codeType;
    }

    public void setCodeType(Type codeType) {
        this.codeType = codeType;
    }

}
