package dtos.responses;

import data.models.Type;

public class ValidateCodeResponse {
    private String residentName;
    private String visitorsName;
    private Type codeType;
    private boolean isValid;

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Type getCodeType() {
        return codeType;
    }

    public void setCodeType(Type codeType) {
        this.codeType = codeType;
    }

    public String getVisitorsName() {
        return visitorsName;
    }

    public void setVisitorsName(String visitorsName) {
        this.visitorsName = visitorsName;
    }
}
