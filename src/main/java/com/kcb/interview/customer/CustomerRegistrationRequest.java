package com.kcb.interview.customer;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerRegistrationRequest {

    @NotEmpty(message = "name is required")
    @NotBlank(message = "name field is required")
    private String name;

    @NotEmpty(message = "email is required")
    @NotBlank(message = "email field is required")
    @Email(message = "Not a valid email provided")
    private String emailAddress;

    @NotEmpty(message = "phoneNumber field is required")
    @NotBlank(message = "phoneNumber field is required")
    private String phoneNumber;

    @NotEmpty(message = "residentialAddress field is required")
    @NotBlank(message = "residentialAddress field is required")
    private String residentialAddress;

    @NotEmpty(message = "gender field is required")
    @NotBlank(message = "gender field is required")
    private String gender;

    @NotEmpty(message = "date of birth field is required")
    @NotBlank(message = "date of birth field is required")
    private String dateOfBirth;
}
