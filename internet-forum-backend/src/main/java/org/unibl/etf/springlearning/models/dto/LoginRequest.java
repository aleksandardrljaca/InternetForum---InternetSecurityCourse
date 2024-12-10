package org.unibl.etf.springlearning.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.unibl.etf.springlearning.models.validators.SqlValidators;
import org.unibl.etf.springlearning.models.validators.XssValidators;

@Data
public class LoginRequest {
    @SqlValidators
    @XssValidators
    @NotBlank
    private String username;
    @SqlValidators
    @XssValidators
    @NotBlank
    private String password;
}
