

public class Patient extends Person {
    private String address;
    private String bloodGroup;

    public Patient() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Override
    public String toString() {
        return  name + " " + prenume + ", " +
                "CNP= " + CNP + ", " +
                "address= " + address + ", " +
                "bloodGroup= " + bloodGroup;
    }
}