package org.unibl.etf.springlearning.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.unibl.etf.springlearning.models.validators.SqlValidators;
import org.unibl.etf.springlearning.models.validators.XssValidators;

@Data
public class UserRegistrationRequest {
    @NotBlank
    @SqlValidators
    @XssValidators
    @Size(max=45)
    private String username;
    @NotBlank
    @SqlValidators
    @XssValidators
    @Size(max=200)
    private String password;
    @NotBlank
    @SqlValidators
    @XssValidators
    @Size(max=45)
    private String firstName;
    @NotBlank
    @SqlValidators
    @XssValidators
    @Size(max=45)
    private String lastName;
    @NotBlank
    @SqlValidators
    @XssValidators
    @Size(max=45)
    private String email;
}
