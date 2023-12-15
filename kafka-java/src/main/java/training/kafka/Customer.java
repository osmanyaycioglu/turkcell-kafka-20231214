package training.kafka;

public class Customer {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer weight;
    private Integer height;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstNameParam) {
        firstName = firstNameParam;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastNameParam) {
        lastName = lastNameParam;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumberParam) {
        phoneNumber = phoneNumberParam;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(final Integer weightParam) {
        weight = weightParam;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(final Integer heightParam) {
        height = heightParam;
    }
}
