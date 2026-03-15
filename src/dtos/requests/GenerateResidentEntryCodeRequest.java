package dtos.requests;

import java.time.LocalDateTime;

public class GenerateResidentEntryCodeRequest {
    private String residentId;
    private LocalDateTime validTill;

    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public LocalDateTime getValidTill() {
        return validTill;
    }

    public void setValidTill(LocalDateTime validTill) {
        this.validTill = validTill;
    }
}
