package dtos.responses;

import data.models.Type;

public class GenerateExitCodeResponse {

    private String code;
    private  String name;
    private Type codeType;
    private String validTill;
    private String houseAddress;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Type getCodeType() {
        return codeType;
    }

    public void setCodeType(Type codeType) {
        this.codeType = codeType;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }
}
