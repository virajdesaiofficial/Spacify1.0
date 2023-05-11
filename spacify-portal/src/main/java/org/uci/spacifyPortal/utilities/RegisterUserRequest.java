package org.uci.spacifyPortal.utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {
    private String userId;

    private String firstName;

    private String lastName;

    private String password;

    private String emailId;

    private String macAddress;

    public RegisterUserRequest() {

    }

    public RegisterUserRequest(String userId, String firstName, String lastName, String password, String emailId, String macAddress) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.emailId = emailId;
        this.macAddress = macAddress;
    }
}
