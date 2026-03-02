package data.models;

public class Resident {
    private String name;
    private int id;
    private String phoneNumber;
    private String  houseAddress;

    public Resident(String name, String id, String phoneNumber, String houseAddress) {
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.houseAddress = houseAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }
}