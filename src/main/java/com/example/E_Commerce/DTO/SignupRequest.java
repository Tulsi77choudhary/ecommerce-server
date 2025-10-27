package com.example.E_Commerce.DTO;

import lombok.Data;

@Data
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public SignupRequest(String firstName, String lastName, String password, String email){
        this.firstName =   firstName;
        this.lastName = lastName;
        this.password= password;
        this.email = email;

    }

}
