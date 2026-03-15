package dtos.requests;

import data.models.Type;

public class ValidateCodeRequest {

    private String code;
    private Type codeType;

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
}
