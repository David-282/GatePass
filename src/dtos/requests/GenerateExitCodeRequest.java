package dtos.requests;

import java.time.LocalDateTime;

public class GenerateExitCodeRequest {
    private String code;
    private LocalDateTime validTill;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getValidTill() {
        return validTill;
    }

    public void setValidTill(LocalDateTime validTill) {
        this.validTill = validTill;
    }
}
