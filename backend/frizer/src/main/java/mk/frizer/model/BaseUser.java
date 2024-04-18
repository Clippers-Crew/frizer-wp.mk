package mk.frizer.model;

public class BaseUser {
    //TODO Id
    //TODO make unique email?
    private String email;
    private String password;
    private String firstName;
    private String lastName;
//   TODO must be unique
    private String phoneNumber;

    public BaseUser() {
    }

    public BaseUser(String email, String password, String firstName, String lastName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
