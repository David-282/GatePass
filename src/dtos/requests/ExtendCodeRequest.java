package dtos.requests;

public class ExtendCodeRequest {

    private String code;
    private int hoursToExtendBy;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getHoursToExtendBy() {
        return hoursToExtendBy;
    }

    public void setHoursToExtendBy(int hoursToExtendBy) {
        this.hoursToExtendBy = hoursToExtendBy;
    }
}
