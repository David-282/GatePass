package dtos.requests;

public class GenerateVisitorEntryCodeRequest {

    private String visitorName;
    private String phoneNumber;
    private String residentId;
    private  int validHour;
    private String purposeOfVisit;

    public String getVisitorName() {
        return visitorName;
    }

    public int getValidHour() {
        return validHour;
    }

    public void setValidHour(int validHour) {
        this.validHour = validHour;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }

    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
