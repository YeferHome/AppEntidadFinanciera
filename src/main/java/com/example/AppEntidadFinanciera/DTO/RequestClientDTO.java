package com.example.AppEntidadFinanciera.DTO;

import java.time.LocalDate;

public class RequestClientDTO {

    private String identityType;
    private int identityNumber;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;

    public String getIdentityType() {
        return identityType;
    }

    public int getIdentityNumber() {
        return identityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
