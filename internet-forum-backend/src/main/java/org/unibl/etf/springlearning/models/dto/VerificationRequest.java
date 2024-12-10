package org.unibl.etf.springlearning.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.unibl.etf.springlearning.models.validators.SqlValidators;
import org.unibl.etf.springlearning.models.validators.XssValidators;

@Data
public class VerificationRequest {
    private Integer userId;
    @NotBlank
    @SqlValidators
    @XssValidators
    @Size(min=6,max=6)
    private String code;
}
